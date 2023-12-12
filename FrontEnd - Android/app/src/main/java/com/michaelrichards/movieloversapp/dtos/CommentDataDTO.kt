package com.michaelrichards.movieloversapp.dtos

import java.time.LocalDateTime

data class CommentDataDTO(
    val id: Long,
    val createdAt: LocalDateTime,
    val lastUpdated: LocalDateTime,
    val commenter: String,
    val description: String
)
