package com.teamsparta.abrasax.domain.member.repository

import com.teamsparta.abrasax.domain.member.model.Member
import jakarta.validation.constraints.Email
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {

    fun findByEmail(email: String):Member?
}