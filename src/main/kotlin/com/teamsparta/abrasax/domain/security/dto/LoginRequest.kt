package com.teamsparta.abrasax.domain.security.dto

data class LoginRequest(
    val email:String,
    val password:String,
)