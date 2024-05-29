package com.teamsparta.abrasax.domain.security.repository

import com.teamsparta.abrasax.domain.security.model.MemberSecurity
import org.springframework.data.jpa.repository.JpaRepository

interface MemberSecurityRepository: JpaRepository<MemberSecurity, Long> {
    fun findByEmail(email:String): MemberSecurity?
}