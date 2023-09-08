package com.MichaelRichards.MovieLovers.services

import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.repositories.EnthusiastRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EnthusiastService(
    private val enthusiastRepository: EnthusiastRepository,
    private val jwtService: JwtService
) : UserDetailsService {


    override fun loadUserByUsername(username: String?): UserDetails =
        username?.let { enthusiastRepository.findByUsername(it) }
            ?: throw UsernameNotFoundException("$username not found")

    fun save(user: Enthusiast): Enthusiast {
        if (user.id == null) {
            user.createdAt = LocalDateTime.now()
        }
        user.updatedAt = LocalDateTime.now()
        return enthusiastRepository.save(user)
    }

    fun findByUsername(username: String): Enthusiast = enthusiastRepository.findByUsername(username) ?: throw UsernameNotFoundException("$username not found")
    fun getUserByBearerToken(bearerToken: String): Enthusiast {
        val jwt: String = bearerToken.substring(7)
        val username = jwtService.extractUsername(jwt)
        return findByUsername(username)
    }

    fun getAllUsers() : List<Enthusiast> = enthusiastRepository.findAll()
}