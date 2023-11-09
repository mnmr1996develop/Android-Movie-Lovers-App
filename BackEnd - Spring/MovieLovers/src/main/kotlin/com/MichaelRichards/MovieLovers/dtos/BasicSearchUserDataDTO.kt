package com.MichaelRichards.MovieLovers.dtos

import java.time.LocalDate

data class BasicSearchUserDataDTO(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val birthday: LocalDate?,
    val following: Boolean,
    val follower: Boolean
){
    override fun toString(): String {
        return "BasicUserDataDTO(firstName='$firstName', lastName='$lastName', email='$email', username='$username', birthday=$birthday)"
    }

}