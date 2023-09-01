package com.MichaelRichards.MovieLovers.repositories

import com.MichaelRichards.MovieLovers.models.Token
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TokenRepository : JpaRepository<Token, UUID> {

    @Query("""select t from Token t inner join Enthusiast e on t.enthusiast.id = e.id where e.id = :enthusiastId and(t.isNotExpired = true or t.isNotRevoked = true)""")
    fun findAllValidTokensByUser(userId: UUID): MutableList<Token>

    fun findByToken(token: String): Token?
}