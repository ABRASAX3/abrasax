package com.teamsparta.abrasax.domain.member.repository

import com.teamsparta.abrasax.domain.member.model.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
}