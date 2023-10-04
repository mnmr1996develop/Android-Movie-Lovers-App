package com.MichaelRichards.MovieLovers.repositories

import com.MichaelRichards.MovieLovers.models.MovieReview
import org.springframework.data.jpa.repository.JpaRepository

interface MovieReviewRepository: JpaRepository<MovieReview, Long> {

    fun findByImdbId(imdbId: String): MutableList<MovieReview>


}
