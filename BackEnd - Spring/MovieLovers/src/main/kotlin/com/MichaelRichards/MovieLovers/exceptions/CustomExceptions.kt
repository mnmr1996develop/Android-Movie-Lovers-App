package com.MichaelRichards.MovieLovers.exceptions

sealed class CustomExceptions(message: String) : RuntimeException(message) {
    class UsernameTakenException(username: String) : CustomExceptions("$username taken")
    class InvalidAge(val age: Long) : CustomExceptions(if (age in 0..12) "$age is too young" else "$age is invalid")
    class EmailTakenException(email: String) : CustomExceptions("$email taken")
    class InvalidTokenException(token: String) : CustomExceptions("$token is invalid")

    class DuplicateReviewException(message: String) : CustomExceptions(message)

    class InvalidReview(message: String) : CustomExceptions(message)

    class InvalidUsername(message: String): CustomExceptions(message)

    class InvalidPassword(message: String): CustomExceptions(message)

    class AlreadyFollowing(username1: String, username2: String): CustomExceptions("$username1 already follows $username2")

    class CannotFindFollowing(username1: String, username2: String): CustomExceptions("$username1 doesn't follow $username2")

}