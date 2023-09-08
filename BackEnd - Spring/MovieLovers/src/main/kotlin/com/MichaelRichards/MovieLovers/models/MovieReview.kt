package com.MichaelRichards.MovieLovers.models

import com.fasterxml.jackson.annotation.JsonIgnore
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
    val rating: Int,

    val description: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    val lastUpdated: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enthusiast_id")
    @JsonIgnore
    val enthusiast: Enthusiast,
){


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MovieReview

        if (id != other.id) return false
        if (imdbId != other.imdbId) return false
        if (createdAt != other.createdAt) return false
        if (enthusiast != other.enthusiast) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + imdbId.hashCode()
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + enthusiast.hashCode()
        return result
    }
}