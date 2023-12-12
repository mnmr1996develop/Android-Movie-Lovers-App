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

    fun findReviewByImdbIdEnthusiast(imdbId: String, enthusiast: Enthusiast) = movieReviewRepository.findByImdbIdAndEnthusiast(imdbId, enthusiast) ?: throw CustomExceptions.InvalidReview("Review_Not_Found")

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

        val savedReview = movieReviewRepository.findByImdbIdAndEnthusiast(movieReview.imdbId, user) ?:
        throw CustomExceptions.InvalidReview("Couldn't Find Review")

        return mapMovieReviewToDTO(savedReview, user)
    }

    fun addReview(review: MovieReviewStarterDTO, username: String): MovieReviewFullDataDTO{
        val user = enthusiastService.findByUsername(username)
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

        val savedReview = movieReviewRepository.findByImdbIdAndEnthusiast(movieReview.imdbId, user) ?:
        throw CustomExceptions.InvalidReview("Couldn't Find Review")
        return mapMovieReviewToDTO(savedReview, user)
    }

    fun updateReview(bearerToken: String, review: MovieReviewStarterDTO): MovieReview{
        val user = enthusiastService.getUserByBearerToken(bearerToken)

        val movieReview = findReviewByImdbIdEnthusiast(review.imdbId, user)

        movieReview.apply {
            description = review.description
            lastUpdated = LocalDateTime.now()
            rating = review.rating
        }
        return movieReviewRepository.save(movieReview)
    }

    fun deleteReview(bearerToken: String, imdbId: String): MovieReview{
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        val review = findReviewByImdbIdEnthusiast(enthusiast = user, imdbId = imdbId)
        user.deleteReview(review)

        throw CustomExceptions.InvalidReview("Review_Not_Found")
    }

    fun deleteReview(bearerToken: String, movieReviewId: Long): MovieReviewFullDataDTO{
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        val review = findById(movieReviewId)
        if (review.enthusiast == user){
            user.deleteReview(review)
            return mapMovieReviewToDTO(review, user)
        }else{
            throw CustomExceptions.InvalidReview("Deleter and User Don't match up")
        }

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

    fun getReviewsByBearerToken(bearerToken: String): List<MovieReviewFullDataDTO> {
        val user = enthusiastService.getUserByBearerToken(bearerToken)

        return mapMovieReviewToDTO(user.movieReviews, user)
    }


    fun getReviewsByUsername(bearerToken: String, username: String): List<MovieReviewFullDataDTO> {


        val user = enthusiastService.findByUsername(username)
        val caller = enthusiastService.getUserByBearerToken(bearerToken)

        return mapMovieReviewToDTO(movieReviewRepository.findByEnthusiastOrderByCreatedAtDesc(enthusiast = user), user)
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
        return savedComment.id?.let {id ->
            CommentFullDTO(id = id , commenter = user.username, createdAt = savedComment.createdAt, lastUpdated = savedComment.updatedAt, description = savedComment.description)
        } ?: throw CustomExceptions.SomethingWentWrong("Comment failed to save")
    }

    fun getMovieReviewComments(bearerToken: String, movieReviewId: Long): List<CommentFullDTO> {
        val movieReview = findById(movieReviewId)




        return commentRepository.findByMovieReview(movieReview).map {comment: Comment ->
            comment.id?.let {id ->
                CommentFullDTO(id = id, createdAt = comment.createdAt, lastUpdated = comment.updatedAt, commenter = comment.enthusiast.username, description = comment.description)
            } ?: throw CustomExceptions.SomethingWentWrong("Could Not Load Comment")
        }
    }

    fun findMovieByImdbId(bearerToken: String, imdbID: String): List<MovieReviewFullDataDTO> {
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        return mapMovieReviewToDTO(movieReviewRepository.findByImdbId(imdbID), user)
    }

    fun getReviewsOfFollowing(bearerToken: String): List<MovieReviewFullDataDTO>? {
        val user = enthusiastService.getUserByBearerToken(bearerToken)

        return mapMovieReviewToDTO(movieReviewRepository.findByEnthusiast_FollowingInOrEnthusiastOrderByCreatedAtDesc(user.followers, user), user)
    }

    private fun mapMovieReviewToDTO(movieReviews: List<MovieReview>, user: Enthusiast) = movieReviews.map {movieReview ->
        MovieReviewFullDataDTO(
            actors = movieReview.actors,
            imdbId = movieReview.imdbId,
            description = movieReview.description,
            imagePosterLink = movieReview.imagePosterLink,
            imdbIdUrl = movieReview.imdbIdUrl,
            rating = movieReview.rating,
            reviewId = movieReview.id!!,
            title = movieReview.title,
            comments = movieReview.comments.size,
            upVotes = movieReview.upVoteCount,
            downVotes = movieReview.downVoteCount,
            userInUpVote = movieReviewRepository.existsByIdAndUpVotes(movieReview.id, user),
            userInDownVote = movieReviewRepository.existsByIdAndDownVotes(movieReview.id, user),
            reviewerUsername = movieReview.enthusiast.username,
            isUserTheReviewer = movieReviewRepository.existsByIdAndEnthusiast(movieReview.id, user),
            lastUpdated = movieReview.lastUpdated,
            createdAt = movieReview.createdAt,
            reviewerFirstName = movieReview.enthusiast.firstName,
            reviewerLastName = movieReview.enthusiast.lastName

        )
    }

    private fun mapMovieReviewToDTO(movieReview: MovieReview, user: Enthusiast) =
        MovieReviewFullDataDTO(
            actors = movieReview.actors,
            imdbId = movieReview.imdbId,
            description = movieReview.description,
            imagePosterLink = movieReview.imagePosterLink,
            imdbIdUrl = movieReview.imdbIdUrl,
            rating = movieReview.rating,
            reviewId = movieReview.id!!,
            title = movieReview.title,
            comments = movieReview.comments.size,
            upVotes = movieReview.upVoteCount,
            downVotes = movieReview.downVoteCount,
            userInUpVote = movieReviewRepository.existsByIdAndUpVotes(movieReview.id, user),
            userInDownVote = movieReviewRepository.existsByIdAndDownVotes(movieReview.id, user),
            reviewerUsername = movieReview.enthusiast.username,
            isUserTheReviewer = movieReviewRepository.existsByIdAndEnthusiast(movieReview.id, user),
            lastUpdated = movieReview.lastUpdated,
            createdAt = movieReview.createdAt,
            reviewerFirstName = movieReview.enthusiast.firstName,
            reviewerLastName = movieReview.enthusiast.lastName

        )



}