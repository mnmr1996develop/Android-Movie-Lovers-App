package com.MichaelRichards.MovieLovers.dtos

import java.time.LocalDate

data class ProfileDataDTO(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val birthday: LocalDate?,
    val followers: Int,
    val following: Int,
    val totalReviews: Int,
    val amIFollowing: Boolean = false,
    val followingUser: Boolean = false
){
    override fun toString(): String {
        return "BasicUserDataDTO(firstName='$firstName', lastName='$lastName', email='$email', username='$username', birthday=$birthday)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProfileDataDTO

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (username != other.username) return false
        if (birthday != other.birthday) return false
        if (followers != other.followers) return false
        if (following != other.following) return false
        if (totalReviews != other.totalReviews) return false
        if (amIFollowing != other.amIFollowing) return false
        if (followingUser != other.followingUser) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + (birthday?.hashCode() ?: 0)
        result = 31 * result + followers
        result = 31 * result + following
        result = 31 * result + totalReviews
        result = 31 * result + amIFollowing.hashCode()
        result = 31 * result + followingUser.hashCode()
        return result
    }

}