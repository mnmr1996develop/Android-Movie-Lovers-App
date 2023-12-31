package com.MichaelRichards.MovieLovers.repositories

import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.Followers
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository : JpaRepository<Followers, Long> {

    fun existsByFollowerAndFollowee(follower: Enthusiast, followee: Enthusiast): Boolean

    fun findByFollowerAndFollowee(follower: Enthusiast, followee: Enthusiast): Followers?

    @Query("select f from Followers f where f.followee = ?1")
    fun findByFollowee(followee: Enthusiast): List<Followers>

    @Query("select f from Followers f where f.followee = ?1")
    fun findByFolloweePageable(followee: Enthusiast, pageable: Pageable
    ): List<Followers>

    @Query("select f from Followers f where f.follower = ?1")
    fun findByFollower(
        follower: Enthusiast
    ): List<Followers>

    @Query("select f from Followers f where f.follower = ?1")
    fun findByFollowerPageable(
        follower: Enthusiast, pageable: Pageable
    ): List<Followers>


}