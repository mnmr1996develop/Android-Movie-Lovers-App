package com.michaelrichards.movieloversapp.dtos

import com.google.gson.annotations.SerializedName

data class UserProfileDTO(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val birthday: String,
    var followers: Int,
    var following: Int,
    var amIFollowing: Boolean,
    var followingUser: Boolean,
    val totalReviews: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserProfileDTO

        if (firstName != other.firstName) return false
        if (lastName != other.lastName) return false
        if (email != other.email) return false
        if (username != other.username) return false
        if (birthday != other.birthday) return false
        if (followers != other.followers) return false
        if (following != other.following) return false
        if (amIFollowing != other.amIFollowing) return false
        if (followingUser != other.followingUser) return false
        if (totalReviews != other.totalReviews) return false

        return true
    }

    override fun hashCode(): Int {
        var result = firstName.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + birthday.hashCode()
        result = 31 * result + followers
        result = 31 * result + following
        result = 31 * result + amIFollowing.hashCode()
        result = 31 * result + followingUser.hashCode()
        result = 31 * result + totalReviews
        return result
    }
}