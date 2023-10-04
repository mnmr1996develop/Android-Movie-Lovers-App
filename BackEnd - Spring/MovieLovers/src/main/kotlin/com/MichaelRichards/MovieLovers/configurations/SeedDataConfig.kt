package com.MichaelRichards.MovieLovers.configurations

import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.Role
import com.MichaelRichards.MovieLovers.repositories.EnthusiastRepository
import com.MichaelRichards.MovieLovers.services.EnthusiastService
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class SeedDataConfig(
    private val enthusiastRepository: EnthusiastRepository,
    private val passwordEncoder: PasswordEncoder,
    private val enthusiastService: EnthusiastService
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        if (enthusiastRepository.count() == 0L){
            val admin = Enthusiast(
                firstName = "admin",
                lastName = "admin",
                email = "admin@gmail.com",
                username = "admin",
                password = passwordEncoder.encode("admin"),
                birthday = LocalDate.now().minusYears(30L),
                role = Role.ROLE_ADMIN,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
            enthusiastService.save(admin)
        }
    }
}