package com.MichaelRichards.MovieLovers.repositories

import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.Followers
import com.MichaelRichards.MovieLovers.models.MovieReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieReviewRepository: JpaRepository<MovieReview, Long> {

    fun findByImdbId(imdbId: String): MutableList<MovieReview>



    fun findByImdbIdAndEnthusiast(imdbId: String, enthusiast: Enthusiast): MovieReview?


    fun existsByImdbIdAndEnthusiast(imdbId: String, enthusiast: Enthusiast): Boolean


    fun findByEnthusiastOrderByCreatedAtDesc(enthusiast: Enthusiast): List<MovieReview>


    fun existsByIdAndUpVotes(id: Long, upVotes: Enthusiast): Boolean


    fun existsByIdAndDownVotes(id: Long, downVotes: Enthusiast): Boolean


    fun existsByIdAndEnthusiast(id: Long, enthusiast: Enthusiast): Boolean

    fun findByEnthusiast_FollowingInOrEnthusiastOrderByCreatedAtDesc(
        followings: MutableCollection<Followers>,
        enthusiast: Enthusiast
    ): List<MovieReview>
}
