package com.MichaelRichards.MovieLovers.dtos

import java.time.LocalDate

data class BasicUserDataDTO(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val birthday: LocalDate?
){
    override fun toString(): String {
        return "BasicUserDataDTO(firstName='$firstName', lastName='$lastName', email='$email', username='$username', birthday=$birthday)"
    }

}