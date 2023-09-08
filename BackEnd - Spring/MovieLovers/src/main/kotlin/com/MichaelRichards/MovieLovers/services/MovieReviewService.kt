package com.MichaelRichards.MovieLovers.services

import com.MichaelRichards.MovieLovers.dtos.MovieReviewDTO
import com.MichaelRichards.MovieLovers.models.MovieReview
import com.MichaelRichards.MovieLovers.repositories.MovieReviewRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class MovieReviewService (
    private val enthusiastService: EnthusiastService,
    private val movieReviewRepository: MovieReviewRepository
){

    fun addReview(bearerToken: String, review: MovieReviewDTO) : MovieReview{
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        val movieReview = MovieReview(
            imdbId = review.imdbId,
            enthusiast = user,
            description = review.description,
            rating = review.rating,
            createdAt = LocalDateTime.now(),
            lastUpdated = LocalDateTime.now()
        )
        user.addReview(movieReview)

        enthusiastService.save(user)

        return movieReviewRepository.save(movieReview)
    }

}