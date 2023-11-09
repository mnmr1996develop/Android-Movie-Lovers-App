package com.MichaelRichards.MovieLovers.dtos

import java.time.LocalDateTime

data class CommentFullDTO(
    val id: Long,
    val createdAt: LocalDateTime,
    val lastUpdated: LocalDateTime,
    val commenter: String,
    val description: String
)
