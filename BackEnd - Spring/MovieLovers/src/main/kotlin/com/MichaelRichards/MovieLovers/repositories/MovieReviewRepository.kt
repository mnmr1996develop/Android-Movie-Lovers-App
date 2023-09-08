package com.MichaelRichards.MovieLovers.repositories

import com.MichaelRichards.MovieLovers.models.MovieReview
import com.MichaelRichards.MovieLovers.models.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface MovieReviewRepository: JpaRepository<MovieReview, Long> {


}
