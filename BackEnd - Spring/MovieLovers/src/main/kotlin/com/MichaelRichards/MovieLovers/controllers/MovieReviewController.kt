package com.MichaelRichards.MovieLovers.controllers

import com.MichaelRichards.MovieLovers.dtos.CommentFullDTO
import com.MichaelRichards.MovieLovers.dtos.CommentInitializerDTO
import com.MichaelRichards.MovieLovers.dtos.MovieReviewFullDataDTO
import com.MichaelRichards.MovieLovers.dtos.MovieReviewStarterDTO
import com.MichaelRichards.MovieLovers.models.MovieReview
import com.MichaelRichards.MovieLovers.services.MovieReviewService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("api/v1/review")
class MovieReviewController(
    private val movieReviewService: MovieReviewService
) {
    companion object {
        private const val BASE_PATH = "/api/v1/review"
        private const val AUTHORIZATION_STRING = "Authorization"
    }

    @GetMapping
    fun getUserReviews(
        @RequestHeader(AUTHORIZATION_STRING) bearerToken: String
    ): ResponseEntity<List<MovieReviewFullDataDTO>> {
        return ResponseEntity.ok(movieReviewService.getReviewsByBearerToken(bearerToken))
    }

    @PostMapping
    fun addReview(
        @RequestHeader(AUTHORIZATION_STRING) bearerToken: String,
        @RequestBody review: MovieReviewStarterDTO
    ): ResponseEntity<MovieReviewFullDataDTO> {
        val uri = URI.create(BASE_PATH)
        return ResponseEntity.created(uri).body(movieReviewService.addReview(bearerToken, review))
    }

    @PatchMapping
    fun updateReview(
        @RequestHeader(AUTHORIZATION_STRING) bearerToken: String,
        @RequestBody review: MovieReviewStarterDTO
    ): ResponseEntity<MovieReview> {
        return ResponseEntity.status(HttpStatus.OK).body(movieReviewService.updateReview(bearerToken, review))
    }

    @DeleteMapping
    fun deleteReview(
        @RequestHeader(AUTHORIZATION_STRING) bearerToken: String,
        @RequestParam movieReviewId: Long
    ): ResponseEntity<MovieReviewFullDataDTO> {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(movieReviewService.deleteReview(bearerToken, movieReviewId))
    }

    @GetMapping("by/{username}")
    fun getMovieReviewByUser(
        @RequestHeader(AUTHORIZATION_STRING) bearerToken: String,
        @PathVariable username: String
    ): ResponseEntity<List<MovieReviewFullDataDTO>> {
        return ResponseEntity.ok(
            movieReviewService.getReviewsByUsername(
                bearerToken = bearerToken,
                username = username
            )
        )
    }

    @GetMapping("followingReviews")
    fun getReviewsOfFollowing(
        @RequestHeader(AUTHORIZATION_STRING) bearerToken: String
    ): ResponseEntity<List<MovieReviewFullDataDTO>> {
        return ResponseEntity.ok(
            movieReviewService.getReviewsOfFollowing(
                bearerToken = bearerToken
            )
        )
    }

    @PostMapping("upVote/{movieReviewId}")
    fun upVoteReview(
        @RequestHeader(AUTHORIZATION_STRING) bearerToken: String,
        @PathVariable movieReviewId: Long
    ): ResponseEntity<MovieReview> {

        return ResponseEntity.ok().body(movieReviewService.upVoteReview(bearerToken, movieReviewId))
    }

    @PostMapping("downVote/{movieReviewId}")
    fun downVoteReview(
        @RequestHeader(AUTHORIZATION_STRING) bearerToken: String,
        @PathVariable movieReviewId: Long
    ): ResponseEntity<MovieReview> {

        return ResponseEntity.ok().body(movieReviewService.downVoteReview(bearerToken, movieReviewId))
    }

    @PostMapping("comments/{movieReviewId}")
    fun addComment(
        @RequestHeader(AUTHORIZATION_STRING) bearerToken: String,
        @PathVariable movieReviewId: Long,
        @RequestBody commentInitializerDTO: CommentInitializerDTO
    ): ResponseEntity<CommentFullDTO> {
        val uri = URI.create("$BASE_PATH/$movieReviewId/comment")
        return ResponseEntity.created(uri)
            .body(movieReviewService.addComment(bearerToken, commentInitializerDTO, movieReviewId))
    }

    @GetMapping("comments/{movieReviewId}")
    fun getCommentsByPost(
        @RequestHeader(AUTHORIZATION_STRING) bearerToken: String,
        @PathVariable movieReviewId: Long
    ): ResponseEntity<List<CommentFullDTO>> {
        return ResponseEntity.ok(
            movieReviewService.getMovieReviewComments(
                bearerToken = bearerToken,
                movieReviewId = movieReviewId
            )
        )
    }

    @GetMapping("of")
    fun findByImbdID(
        @RequestHeader(AUTHORIZATION_STRING) bearerToken: String,
        @RequestParam imbdID: String
    ): ResponseEntity<List<MovieReviewFullDataDTO>>{
        return ResponseEntity.ok().body(movieReviewService.findMovieByImdbId(bearerToken, imbdID))
    }

}