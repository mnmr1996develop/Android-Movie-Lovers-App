package com.MichaelRichards.MovieLovers.controllers

import com.MichaelRichards.MovieLovers.dtos.MovieReviewDTO
import com.MichaelRichards.MovieLovers.models.MovieReview
import com.MichaelRichards.MovieLovers.services.MovieReviewService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("api/v1/review")
class MovieReviewController(
    private val movieReviewService: MovieReviewService
) {
    companion object {
        private const val BASE_PATH = "/api/v1/review"
    }

    @PostMapping
    fun addReview(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestBody review: MovieReviewDTO
    ): ResponseEntity<MovieReview> {
        val uri = URI.create(BASE_PATH)
        return ResponseEntity.created(uri).body(movieReviewService.addReview(bearerToken, review))
    }

    @PatchMapping
    fun updateReview(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestBody review: MovieReviewDTO
    ): ResponseEntity<MovieReview> {
        val uri = URI.create(BASE_PATH)
        return ResponseEntity.status(HttpStatus.OK).body(movieReviewService.updateReview(bearerToken, review))
    }

    @DeleteMapping
    fun deleteReview(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestBody imdbId: String
    ): ResponseEntity<MovieReview> {
        val uri = URI.create(BASE_PATH)
        return ResponseEntity.created(uri).body(movieReviewService.deleteReview(bearerToken, imdbId))
    }

}