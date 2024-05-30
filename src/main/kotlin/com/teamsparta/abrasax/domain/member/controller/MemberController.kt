package com.teamsparta.abrasax.domain.member.controller

import com.teamsparta.abrasax.domain.member.dto.*
import com.teamsparta.abrasax.domain.member.service.MemberService
import com.teamsparta.abrasax.domain.member.authentication.dto.SignUpRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping("/members")
@RestController
class MemberController(
    private val memberService: MemberService,

) {

    @PostMapping("/")
    fun signUp(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<MemberResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.signUp(signUpRequest))
    }

//    @PostMapping("/login")
//    fun login(@RequestBody loginRequest: LoginRequest):ResponseEntity<String>{
//        val token= authenticationService.login
//    }


    @PutMapping("/{memberId}/profile")
    fun updateProfile(
        @PathVariable memberId: Long,
        @RequestBody updateProfileRequest: UpdateProfileRequest
    ): ResponseEntity<MemberResponse> {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateProfile(memberId, updateProfileRequest))
    }

}




