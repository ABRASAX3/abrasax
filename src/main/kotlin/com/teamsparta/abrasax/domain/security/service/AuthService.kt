package com.teamsparta.abrasax.domain.security.service

import com.teamsparta.abrasax.common.security.JwtTokenProvider
import com.teamsparta.abrasax.domain.member.dto.SignUpRequest
import com.teamsparta.abrasax.domain.security.dto.LoginRequest
import com.teamsparta.abrasax.domain.security.model.MemberSecurity
import com.teamsparta.abrasax.domain.security.repository.MemberSecurityRepository
import org.springframework.security.core.Authentication
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManager: AuthenticationManager, // spring security에서 제공하는 인터페이스(인증을 관리하고 수행)
    private val jwtTokenProvider: JwtTokenProvider, // JWT토큰 만드는 클래스 주입
    private val passwordEncoder: PasswordEncoder, // 비밀번호 암호화
    private val memberSecurityRepository: MemberSecurityRepository //리포지토리 연결
) {

    //이메일이랑 비밀번호 받아서 로그인 하는 함수
    fun login(loginRequest: LoginRequest): String? {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        ) //authenticationManager.authenticate를 이용해서 사용자 인증을 시도하고 매개변수로 UsernamePasswordAuthenticationToken 객체를 생성해서 이메일이랑 비밀번호를 전달함
// 즉 authetication이라는 변수에 사용자의 인증 정보가 담겨져 있는 상태

        // **********************지금 데이터베이스의 이메일 비밀번호와 사용자가 입력한 이메일 비밀번호가 일치하는지 아닌지를 확인해야함********************************

        if (authentication.isAuthenticated) { // 성공적으로 인증되었는지 확인
            return jwtTokenProvider.generateToken(loginRequest.email) // 성공적으로 인증되었다면 이메일을 기반으로 JWT토큰을 생성해서 반환함
        }
        return null //인증 실패했으면 null반환
    }


    fun register(signUpRequest: SignUpRequest){ //회원가입할때 사용자를 등록하는 함수
        val memberSecurity = MemberSecurity(email = signUpRequest.email, password = passwordEncoder.encode(signUpRequest.password)) // membersecurity dto에서 입력받은 email이랑 암호화된 비밀번호를 해당 변수에 저장
        memberSecurityRepository.save(memberSecurity) //데이터베이스에 암호화된 비밀번호랑 이메일을 저장함
    }


}