package com.MichaelRichards.MovieLovers.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Comment (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "commentId", nullable = false)
    var id: Long? = null,

    val createdAt: LocalDateTime,

    var updatedAt: LocalDateTime,

    var description: String,

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "enthusiast_id")
    val enthusiast: Enthusiast,

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "movie_review_id")
    val movieReview: MovieReview

){



    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Comment

        if (id != other.id) return false
        if (createdAt != other.createdAt) return false
        if (updatedAt != other.updatedAt) return false
        if (description != other.description) return false
        if (enthusiast != other.enthusiast) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + createdAt.hashCode()
        result = 31 * result + updatedAt.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + (enthusiast.hashCode() ?: 0)
        return result
    }



}