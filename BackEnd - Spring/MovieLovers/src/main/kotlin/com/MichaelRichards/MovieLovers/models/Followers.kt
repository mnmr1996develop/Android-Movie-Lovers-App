package com.MichaelRichards.MovieLovers.models

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Followers(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    val follower: Enthusiast,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id")
    val followee: Enthusiast,

    val localDateTime: LocalDateTime
) {
    override fun toString(): String {
        return "Followers(id=$id, follower=${follower.username}, followee=${followee.username})"
    }
}