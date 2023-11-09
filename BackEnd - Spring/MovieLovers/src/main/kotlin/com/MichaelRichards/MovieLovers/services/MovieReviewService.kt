package com.MichaelRichards.MovieLovers.services

import com.MichaelRichards.MovieLovers.dtos.CommentFullDTO
import com.MichaelRichards.MovieLovers.dtos.CommentInitializerDTO
import com.MichaelRichards.MovieLovers.dtos.MovieReviewFullDataDTO
import com.MichaelRichards.MovieLovers.dtos.MovieReviewStarterDTO
import com.MichaelRichards.MovieLovers.exceptions.CustomExceptions
import com.MichaelRichards.MovieLovers.models.Comment
import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.MovieReview
import com.MichaelRichards.MovieLovers.repositories.CommentRepository
import com.MichaelRichards.MovieLovers.repositories.MovieReviewRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class MovieReviewService (
    private val enthusiastService: EnthusiastService,
    private val movieReviewRepository: MovieReviewRepository,
    private val commentRepository: CommentRepository
){

    fun findById(id: Long): MovieReview = movieReviewRepository.findById(id).orElseThrow { CustomExceptions.InvalidReview("Review_Not_Found") }

    fun findReviewByimbdIdEnthusiast(imdbId: String, enthusiast: Enthusiast) = movieReviewRepository.findByImdbIdAndEnthusiast(imdbId, enthusiast) ?: throw CustomExceptions.InvalidReview("Review_Not_Found")

    fun addReview(bearerToken: String, review: MovieReviewStarterDTO) : MovieReviewFullDataDTO {
        val user = enthusiastService.getUserByBearerToken(bearerToken)

        if (movieReviewRepository.existsByImdbIdAndEnthusiast(review.imdbId, enthusiast = user)) throw CustomExceptions.DuplicateReviewException("Cannot Review A Movie Twice")

        val movieReview = MovieReview(
            imdbId = review.imdbId,
            enthusiast = user,
            description = review.description,
            rating = review.rating,
            title = review.title,
            actors = review.actors,
            imagePosterLink = review.imagePosterLink,
            imdbIdUrl = review.imdbIdUrl,
            createdAt = LocalDateTime.now(),
            lastUpdated = LocalDateTime.now()
        )
        user.addReview(movieReview)

        enthusiastService.save(user)

        val savedReview = movieReviewRepository.findByImdbIdAndEnthusiast(movieReview.imdbId, user) ?: throw CustomExceptions.InvalidReview("Couldn't Find Review")


        return MovieReviewFullDataDTO(
            reviewId = savedReview.id!!,
            actors = savedReview.actors,
            description = savedReview.description,
            imdbIdUrl = savedReview.imdbIdUrl,
            imagePosterLink = savedReview.imagePosterLink,
            imdbId = savedReview.imdbId,
            rating = savedReview.rating,
            title = savedReview.title
        )
    }

    fun updateReview(bearerToken: String, review: MovieReviewStarterDTO): MovieReview{
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        for (userReview in user.movieReviews){
            if (userReview.imdbId == review.imdbId){
                userReview.description = review.description
                userReview.lastUpdated = LocalDateTime.now()
                userReview.rating = review.rating
                return movieReviewRepository.save(userReview)
            }
        }
        throw CustomExceptions.InvalidReview("Review_Not_Found")
    }

    fun deleteReview(bearerToken: String, imdbId: String): MovieReview{
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        val review = findReviewByimbdIdEnthusiast(enthusiast = user, imdbId = imdbId)
        user.deleteReview(review)

        throw CustomExceptions.InvalidReview("Review_Not_Found")
    }

    fun getMovieAverage(imdbName: String): Float{
        val reviews = movieReviewRepository.findByImdbId(imdbName)
        if (reviews.isEmpty()) return 0f

        var cumRating = 0f

        for (review in reviews){
            cumRating+= review.rating.toFloat()
        }

        return cumRating/reviews.size
    }

    fun getMyMovieReviews(bearerToken: String): List<MovieReviewFullDataDTO> {
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        return user.movieReviews
            .map { movieReview: MovieReview ->
                MovieReviewFullDataDTO(
                    actors = movieReview.actors,
                    imdbId = movieReview.imdbId,
                    description = movieReview.description,
                    imagePosterLink = movieReview.imagePosterLink,
                    imdbIdUrl = movieReview.imdbIdUrl,
                    rating = movieReview.rating,
                    reviewId = movieReview.id!!,
                    title = movieReview.title
                )
        }
    }

    fun upVoteReview(bearerToken: String, movieReviewId: Long): MovieReview {
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        val movieReview = findById(movieReviewId)
        movieReview.upVote(user)
        return movieReviewRepository.save(movieReview)
    }

    fun downVoteReview(bearerToken: String, movieReviewId: Long): MovieReview {
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        val movieReview = findById(movieReviewId)
        movieReview.downVote(user)
        return movieReviewRepository.save(movieReview)
    }


    fun addComment(bearerToken: String, commentInitializerDTO: CommentInitializerDTO, movieReviewId: Long): CommentFullDTO{
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        val movieReview = findById(movieReviewId)
        val comment = Comment(
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now(),
            enthusiast = user,
            description = commentInitializerDTO.description,
            movieReview = movieReview
        )

        user.addComment(comment)
        movieReview.addComment(comment)
        val savedComment = commentRepository.save(comment)

        return CommentFullDTO(id = savedComment.id!!, commenter = user.username, createdAt = savedComment.createdAt, lastUpdated = savedComment.updatedAt, description = savedComment.description)
    }

    fun getMovieReviewComments(bearerToken: String, movieReviewId: Long, pageNumber: Int): List<CommentFullDTO> {
        val movieReview = findById(movieReviewId)

        if (pageNumber < 0) {
            throw IndexOutOfBoundsException("")
        }

        val pageable: Pageable = PageRequest.of(pageNumber, 20)
        return commentRepository.findByMovieReview(movieReview, pageable).map {comment: Comment ->
            CommentFullDTO(id = comment.id!!, createdAt = comment.createdAt, lastUpdated = comment.updatedAt, commenter = comment.enthusiast.username, description = comment.description)
        }
    }
}