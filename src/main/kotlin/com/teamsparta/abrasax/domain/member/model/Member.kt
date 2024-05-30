package com.teamsparta.abrasax.domain.member.model

import com.teamsparta.abrasax.domain.helper.ListStringifyHelper
import com.teamsparta.abrasax.domain.member.dto.MemberResponse
import jakarta.persistence.*
import java.util.regex.Pattern

@Entity
@Table(name = "member")
class Member(
    @Column(name = "nickname")
    var nickname: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "email")
    var email: String,

    @Column(name = "social_accounts")
    var stringifiedSocialAccounts: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    fun updateProfile(newSocialAccounts: List<String>, newNickname: String) {
        validateNickname(nickname)
        nickname = newNickname
        stringifiedSocialAccounts = ListStringifyHelper.stringifyList(newSocialAccounts)


    }

    fun updatePassword(newPassword: String) {
        validatePassword(password)
        checkingDuplicatedPassword(password, newPassword)
        password = newPassword

    }

    companion object {
        private fun validateNickname(nickname: String) {
            if (nickname.length > 10) {
                throw IllegalArgumentException("Nickname must be less than 10 characters.")
            }
            if(!Pattern.matches("^[a-zA-Z0-9]*$",nickname)){ //특수문자 확인
                throw IllegalArgumentException("Invalid nickname format.")
            }
        }

        private fun validateEmail(email: String){

            if(!Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",email)){
                throw IllegalArgumentException("Invalid email format.")
            }
        }
        private fun validatePassword(password: String){

            if(!Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",password)){
                throw IllegalArgumentException("Invalid password format.")
            }
            if(password.length<8 || password.length>20){
                throw IllegalArgumentException("Password must be more than 8 characters and less than 20 characters.")
            }

            //"^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#\$%^&*()_+\\-=[\\]{};':\"\\\\|,.<>/?]).{6,}$" 특수문자까지 포함한 비밀번호 규칙
        }
        private fun checkingDuplicatedPassword(currentPassword: String, newPassword: String){
            if(currentPassword==newPassword){
                throw IllegalArgumentException("Passwords are equal")
            }
        }



        fun of(email: String, nickname: String, password: String): Member {
            validateNickname(nickname)
            validateEmail(email)
            validatePassword(password)

            return Member(
                email = email,
                nickname = nickname,
                password = password,
                stringifiedSocialAccounts = ""
            )
        }
    }
}

fun Member.toResponse(): MemberResponse {
    return MemberResponse(
        id = id!!,
    )
}