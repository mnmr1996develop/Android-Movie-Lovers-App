package com.michaelrichards.movieloversapp.dtos

import androidx.compose.runtime.MutableState

data class SearchDataDTO(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val birthday: String,
    var following: Boolean,
    var follower: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SearchDataDTO

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (username != other.username) return false
        if (birthday != other.birthday) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + birthday.hashCode()
        return result
    }
}