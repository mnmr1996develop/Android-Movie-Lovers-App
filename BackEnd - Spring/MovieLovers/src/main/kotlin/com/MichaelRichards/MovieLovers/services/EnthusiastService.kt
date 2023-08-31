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
    private val enthusiastRepository: EnthusiastRepository
) : UserDetailsService {


    override fun loadUserByUsername(username: String?): UserDetails =
        username?.let { enthusiastRepository.findByUsername(it) }
            ?: throw UsernameNotFoundException("$username not found")

    fun save(newUser: Enthusiast): Enthusiast {
        if (newUser.id == null) {
            newUser.createdAt = LocalDateTime.now()
        }
        newUser.updatedAt = LocalDateTime.now()
        return enthusiastRepository.save(newUser)
    }
}