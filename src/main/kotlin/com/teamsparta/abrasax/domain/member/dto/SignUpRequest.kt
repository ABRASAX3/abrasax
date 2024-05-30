package com.teamsparta.abrasax.domain.member.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class SignUpRequest(

    @field:Email
    val email: String,

    @field:Size(min = 1, max = 10, message = "비밀번호는 10자까지 입니다.")
    val password: String,


    val nickname: String,
)