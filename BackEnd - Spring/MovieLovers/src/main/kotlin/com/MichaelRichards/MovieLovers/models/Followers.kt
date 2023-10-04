package com.MichaelRichards.MovieLovers.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Followers(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long? = null,


    @ManyToOne
    @JoinColumn(name = "follower_id")
    val follower: Enthusiast,

    @ManyToOne
    @JoinColumn(name = "following_id")
    val followee: Enthusiast
) {
    override fun toString(): String {
        return "Followers(id=$id, follower=${follower.username}, followee=${followee.username})"
    }
}