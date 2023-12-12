package com.MichaelRichards.MovieLovers.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.time.LocalDateTime

@Entity
@NamedQueries(value = [
    NamedQuery(name = "MovieReview.findByTitle", query = "select m from MovieReview m where m.title = :title")
])
class MovieReview (

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = 0,

    val imdbId: String,

    val title: String,

    val imagePosterLink: String,

    val actors: String,

    val imdbIdUrl: String,

    @Min(0)
    @Max(10)
    var rating: Int,

    @Column(length = 2000)
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
    val upVotes: MutableList<Enthusiast> = mutableListOf()

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    val downVotes: MutableList<Enthusiast> = mutableListOf()

    @JsonIgnore
    @OneToMany(mappedBy = "movieReview", cascade = [CascadeType.ALL], orphanRemoval = true)
    var comments: MutableSet<Comment> = mutableSetOf()

    val upVoteCount get() = upVotes.size

    val downVoteCount get() = downVotes.size



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

        if (downVotes.contains(enthusiast)){
            downVotes.remove(enthusiast)
        }

        if (!upVotes.contains(enthusiast))
            upVotes.add(enthusiast)
    }

    fun downVote(enthusiast: Enthusiast){
        if (upVotes.contains(enthusiast)){
            upVotes.remove(enthusiast)
        }

        if (!downVotes.contains(enthusiast))
            downVotes.add(enthusiast)
    }

    fun addComment(comment: Comment) {
        if (comments.contains(comment)) return
        comments.add(comment)
    }


}