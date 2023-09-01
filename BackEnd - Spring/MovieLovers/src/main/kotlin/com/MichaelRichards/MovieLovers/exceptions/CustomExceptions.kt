package com.MichaelRichards.MovieLovers.exceptions

sealed class CustomExceptions(message: String) : RuntimeException(message) {
    class UsernameTakenException(username: String): CustomExceptions("$username taken")
    class InvalidAge(val age: Long): CustomExceptions(if (age in 1..12) "$age is too young" else "$age is invalid")
    class EmailTakenException(email: String): CustomExceptions("$email taken")
    class InvalidTokenException(message: String): CustomExceptions(message)
}