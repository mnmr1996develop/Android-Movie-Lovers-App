package com.MichaelRichards.MovieLovers.dtos

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import java.time.LocalDate

data class SignUpRequest(
    val firstName: String,
    val lastName: String,
    @field:Email(message = "Need Valid E-mail")
    val email: String,
    val username: String,
    val password: String,
    val birthday: LocalDate,
)
