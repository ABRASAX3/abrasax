package com.teamsparta.abrasax.domain.member.authentication.service

import com.teamsparta.abrasax.common.security.JwtTokenProvider
import com.teamsparta.abrasax.domain.exception.MemberNotFoundException
import com.teamsparta.abrasax.domain.exception.PasswordNotMatchException
import com.teamsparta.abrasax.domain.member.authentication.dto.LoginRequest
import com.teamsparta.abrasax.domain.member.authentication.dto.LoginResponse
import com.teamsparta.abrasax.domain.member.authentication.dto.SignUpRequest
import com.teamsparta.abrasax.domain.member.authentication.dto.UpdatePasswordRequest
import com.teamsparta.abrasax.domain.member.dto.MemberResponse
import com.teamsparta.abrasax.domain.member.model.Member
import com.teamsparta.abrasax.domain.member.model.toResponse
import com.teamsparta.abrasax.domain.member.repository.MemberRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager, // spring security에서 제공하는 인터페이스(인증을 관리하고 수행)
    private val jwtTokenProvider: JwtTokenProvider, // JWT토큰 만드는 클래스 주입
    private val passwordEncoder: PasswordEncoder, // 비밀번호 암호화
    private val memberRepository: MemberRepository, //리포지토리 연결
) {

    //이메일이랑 비밀번호 받아서 로그인 하는 함수
    @Transactional
    fun login(loginRequest: LoginRequest): LoginResponse {
        authenticationManager.authenticate(//데이터베이스와의 비교는 Spring Security가 내부적으로 처리하며 그 설정은 SecurityConfig안에 이
            UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        ) //authenticationManager.authenticate를 이용해서 사용자 인증을 시도하고 매개변수로 UsernamePasswordAuthenticationToken 객체를 생성해서 이메일이랑 비밀번호를 전달함
// 즉 authetication이라는 변수에 사용자의 인증 정보가 담겨져 있는 상태

        // if(loginRequest.password==) 식으로 데이터베이스의 이메일 비밀번호와 일치하는지를 통한 것이 아닌 authenticationManager.authenticate()함수를 이용해서 인증을 함
        return LoginResponse(token = jwtTokenProvider.generateToken(loginRequest.email))  // 성공적으로 인증되었다면 이메일을 기반으로 JWT토큰을 생성해서 반환함
    }

    @Transactional
    fun signUp(signUpRequest: SignUpRequest) { //회원가입할때 사용자를 등록하는 함수
        val (email, password, nickname) = signUpRequest
        val member = Member.of(
            email = email,
            password = passwordEncoder.encode(password),
            nickname = nickname
        ) // membersecurity dto에서 입력받은 email이랑 암호화된 비밀번호를 해당 변수에 저장
        memberRepository.save(member) //데이터베이스에 암호화된 비밀번호랑 이메일을 저장함
    }

    @Transactional
    fun updatePassword(id: Long, request: UpdatePasswordRequest): MemberResponse {
        val member =
            memberRepository.findByIdOrNull(id) ?: throw MemberNotFoundException(id)
        val (currentPassword, newPassword) = request
        if (member.password != passwordEncoder.encode(currentPassword)) throw PasswordNotMatchException()
        member.updatePassword(passwordEncoder.encode(newPassword))
        return member.toResponse()
    }

}