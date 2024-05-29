package com.teamsparta.abrasax.domain.security.model

import jakarta.persistence.*


@Entity
@Table(name="memberSecurity")
data class MemberSecurity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name="email")
    val email: String,

    @Column(name="encoding_password")
    val password: String
)
