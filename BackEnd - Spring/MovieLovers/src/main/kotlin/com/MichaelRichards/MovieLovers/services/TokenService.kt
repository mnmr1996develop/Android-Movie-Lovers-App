package com.MichaelRichards.MovieLovers.services

import com.MichaelRichards.MovieLovers.models.Token
import com.MichaelRichards.MovieLovers.repositories.TokenRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
    private val tokenRepository: TokenRepository
) {

    fun findTokenByName(token: String): Token = tokenRepository.findByToken(token) ?: throw Exception("")

    fun saveToken(token: Token) = tokenRepository.save(token)
    fun findAllValidTokensByUser(id: UUID) = tokenRepository.findAllValidTokensByUser(id)
    fun saveAll(tokens: MutableList<Token>): MutableList<Token> = tokenRepository.saveAll(tokens.asIterable())
}