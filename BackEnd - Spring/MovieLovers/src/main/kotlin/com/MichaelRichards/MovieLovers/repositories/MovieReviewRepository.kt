package com.MichaelRichards.MovieLovers.repositories

import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.MovieReview
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieReviewRepository: JpaRepository<MovieReview, Long> {

    fun findByImdbId(imdbId: String): MutableList<MovieReview>


    fun findByImdbIdAndEnthusiast(imdbId: String, enthusiast: Enthusiast): MovieReview?


    fun existsByImdbIdAndEnthusiast(imdbId: String, enthusiast: Enthusiast): Boolean
}
