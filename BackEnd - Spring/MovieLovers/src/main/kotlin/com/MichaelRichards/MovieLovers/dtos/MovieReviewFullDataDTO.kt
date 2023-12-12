package com.MichaelRichards.MovieLovers.dtos

import java.time.LocalDateTime

data class MovieReviewFullDataDTO(
    val reviewId: Long,
    val imdbId: String,
    val reviewerUsername: String,
    val reviewerFirstName: String,
    val reviewerLastName: String,
    val rating: Int,
    val title: String,
    val imagePosterLink: String,
    val actors: String,
    val imdbIdUrl: String,
    val description: String,
    val comments: Int,
    val upVotes: Int,
    val downVotes: Int,
    val userInUpVote: Boolean,
    val userInDownVote: Boolean,
    val lastUpdated: LocalDateTime,
    val createdAt: LocalDateTime,
    val isUserTheReviewer: Boolean
)
