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
    var rating: Float,

    var description: String,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var lastUpdated: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "enthusiast_id")
    @JsonIgnore
    val enthusiast: Enthusiast,
){

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private val _upVotes: MutableList<Enthusiast> = mutableListOf()

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    private val _downVotes: MutableList<Enthusiast> = mutableListOf()

    @get:JsonIgnore
    val upVotes get() = _upVotes.toList()

    val upVoteCount get() = _upVotes.size

    @get:JsonIgnore
    val downVotes get() = _downVotes.toList()

    val downVoteCount get() = _downVotes.size


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

    fun upVote(enthusiast: Enthusiast){

        if (_downVotes.contains(enthusiast)){
            _downVotes.remove(enthusiast)
        }

        if (!_upVotes.contains(enthusiast))
            _upVotes.add(enthusiast)
    }

    fun downVote(enthusiast: Enthusiast){
        if (_upVotes.contains(enthusiast)){
            _upVotes.remove(enthusiast)
        }

        if (!_downVotes.contains(enthusiast))
            _downVotes.add(enthusiast)
    }
}