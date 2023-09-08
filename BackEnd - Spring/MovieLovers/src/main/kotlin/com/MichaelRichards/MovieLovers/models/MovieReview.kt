package com.MichaelRichards.MovieLovers.models

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.time.LocalDateTime

@Entity
class MovieReview (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = 0,

    val imdbId: String,

    @Min(0)
    @Max(10)
    val rating: Float,

    val description: String = "",

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val lastUpdated: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enthusiast_id")
    val enthusiast: Enthusiast,
){


}