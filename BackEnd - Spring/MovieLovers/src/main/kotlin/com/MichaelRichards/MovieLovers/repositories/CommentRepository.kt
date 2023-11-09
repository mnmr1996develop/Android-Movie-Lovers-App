package com.MichaelRichards.MovieLovers.repositories

import com.MichaelRichards.MovieLovers.models.Comment
import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.MovieReview
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {


    fun findByEnthusiast(enthusiast: Enthusiast): List<Comment>


    fun findByMovieReview(movieReview: MovieReview, pageable: Pageable): List<Comment>
}