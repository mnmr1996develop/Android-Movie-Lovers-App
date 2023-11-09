package com.MichaelRichards.MovieLovers.configurations

import com.MichaelRichards.MovieLovers.dtos.MovieReviewStarterDTO
import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.Role
import com.MichaelRichards.MovieLovers.repositories.EnthusiastRepository
import com.MichaelRichards.MovieLovers.services.EnthusiastService
import com.MichaelRichards.MovieLovers.services.MovieReviewService
import com.MichaelRichards.MovieLovers.services.TokenService
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class SeedDataConfig(
    private val enthusiastRepository: EnthusiastRepository,
    private val passwordEncoder: PasswordEncoder,
    private val enthusiastService: EnthusiastService,
    private val movieReviewService: MovieReviewService,
    private val tokenService: TokenService,
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

            makeSomeUsers()


        }
    }

    private fun makeSomeUsers() {
        for (i in 0..26) {
            val user = Enthusiast(
                firstName = "Micha${'a' + i}el",
                lastName = "${'A' + i}ichards",
                email = "mnmr199${i}@gmail.com",
                username = "mnmr199${i}",
                password = passwordEncoder.encode("Password2_"),
                birthday = LocalDate.of(1980 + i, 11, 15),
                role = Role.ROLE_USER,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
            enthusiastService.save(user)
        }

        for (i in 26 downTo 1) {
            enthusiastService.followForSeedData("mnmr199${i}", "mnmr199${i - 1}")
        }

        for (i in 0..25 step 2) {
            enthusiastService.followForSeedData("mnmr199${i}", "mnmr199${i + 1}")
        }

        enthusiastService.followForSeedData("mnmr1990", "mnmr1993")
        enthusiastService.followForSeedData("mnmr1990", "mnmr1995")
        enthusiastService.followForSeedData("mnmr1990", "mnmr1997")
        enthusiastService.followForSeedData("mnmr1990", "mnmr1999")

        enthusiastService.followForSeedData("mnmr1991", "mnmr1996")
        enthusiastService.followForSeedData("mnmr1991", "mnmr1997")
        enthusiastService.followForSeedData("mnmr1991", "mnmr1999")
        enthusiastService.followForSeedData("mnmr1991", "mnmr19910")

        enthusiastService.followForSeedData("mnmr1992", "mnmr1994")
        enthusiastService.followForSeedData("mnmr1992", "mnmr1997")
        enthusiastService.followForSeedData("mnmr1992", "mnmr1998")

        enthusiastService.followForSeedData("mnmr1993", "mnmr1990")
        enthusiastService.followForSeedData("mnmr1993", "mnmr1991")
        enthusiastService.followForSeedData("mnmr1993", "mnmr1995")
        enthusiastService.followForSeedData("mnmr1993", "mnmr1999")

        enthusiastService.followForSeedData("mnmr1994", "mnmr1991")
        enthusiastService.followForSeedData("mnmr1994", "mnmr1992")
        enthusiastService.followForSeedData("mnmr1994", "mnmr1996")
        enthusiastService.followForSeedData("mnmr1994", "mnmr1998")
        enthusiastService.followForSeedData("mnmr1994", "mnmr19910")

        enthusiastService.followForSeedData("mnmr1995", "mnmr1990")
        enthusiastService.followForSeedData("mnmr1995", "mnmr1993")
        enthusiastService.followForSeedData("mnmr1995", "mnmr1996")
        enthusiastService.followForSeedData("mnmr1995", "mnmr1998")
        enthusiastService.followForSeedData("mnmr1995", "mnmr1999")
        enthusiastService.followForSeedData("mnmr1995", "mnmr19910")


        enthusiastService.followForSeedData("mnmr1996", "mnmr1990")
        enthusiastService.followForSeedData("mnmr1996", "mnmr1991")
        enthusiastService.followForSeedData("mnmr1996", "mnmr1992")
        enthusiastService.followForSeedData("mnmr1996", "mnmr1993")
        enthusiastService.followForSeedData("mnmr1996", "mnmr1994")
        enthusiastService.followForSeedData("mnmr1996", "mnmr1998")
        enthusiastService.followForSeedData("mnmr1996", "mnmr1999")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19910")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19911")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19912")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19913")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19914")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19915")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19916")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19917")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19918")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19919")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19920")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19921")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19922")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19923")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19924")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19925")
        enthusiastService.followForSeedData("mnmr1996", "mnmr19926")



        enthusiastService.followForSeedData("mnmr1997", "mnmr1990")
        enthusiastService.followForSeedData("mnmr1997", "mnmr1992")
        enthusiastService.followForSeedData("mnmr1997", "mnmr1994")
        enthusiastService.followForSeedData("mnmr1997", "mnmr1995")
        enthusiastService.followForSeedData("mnmr1997", "mnmr19910")

        enthusiastService.followForSeedData("mnmr1998", "mnmr1990")
        enthusiastService.followForSeedData("mnmr1998", "mnmr1991")
        enthusiastService.followForSeedData("mnmr1998", "mnmr1992")

        enthusiastService.followForSeedData("mnmr1999", "mnmr1993")
        enthusiastService.followForSeedData("mnmr1999", "mnmr1995")

        enthusiastService.followForSeedData("mnmr19910", "mnmr1991")
        enthusiastService.followForSeedData("mnmr19910", "mnmr1994")
        enthusiastService.followForSeedData("mnmr19910", "mnmr1996")
        enthusiastService.followForSeedData("mnmr19910", "mnmr1998")
    }



}