package com.teamsparta.abrasax.domain.security.controller

import com.teamsparta.abrasax.domain.security.dto.LoginRequest
import com.teamsparta.abrasax.domain.member.dto.SignUpRequest
import com.teamsparta.abrasax.domain.security.model.MemberSecurity
import com.teamsparta.abrasax.domain.security.service.AuthService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthenticationController(
    private val authService: AuthService // 컨트롤러에서 서비스 연결
) {

    @PostMapping("/register")
    fun register(@RequestBody signupRequest: SignUpRequest): ResponseEntity.BodyBuilder {
        authService.register(signupRequest)
        return ResponseEntity.status(HttpStatus.CREATED)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<String> {
        val token = authService.login(loginRequest)
        //authService에서 인증이 성공하면 토큰이 반환되므로 token 변수에 token정보가 담기고 인증이 실패하면 token 변수에 null이 담김
        return if (token != null) {
            ResponseEntity.ok(token) //이 부분에서 인증이 된 상태, 컨트롤러를 따로 만드는게 아닌 기존의 memberController에 추가하기?
        } else {
            ResponseEntity.status(401).body("인증 실패로 해당 토큰이 존재하지 않습니다")
        }
    }
}