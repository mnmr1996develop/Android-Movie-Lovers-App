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
)