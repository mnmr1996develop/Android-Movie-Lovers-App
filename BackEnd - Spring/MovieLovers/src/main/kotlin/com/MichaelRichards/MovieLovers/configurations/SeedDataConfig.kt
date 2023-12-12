package com.MichaelRichards.MovieLovers.configurations

import com.MichaelRichards.MovieLovers.dtos.MovieReviewStarterDTO
import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.Role
import com.MichaelRichards.MovieLovers.repositories.EnthusiastRepository
import com.MichaelRichards.MovieLovers.services.EnthusiastService
import com.MichaelRichards.MovieLovers.services.MovieReviewService
import com.MichaelRichards.MovieLovers.services.TokenService
import org.apache.coyote.http11.Constants.a
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
        if (enthusiastRepository.count() == 0L) {
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

            makeSomePost()

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


    private fun makeSomePost() {

        movieReviewService.addReview(
            username = "mnmr1995",
            review = MovieReviewStarterDTO(
                imdbId = "tt4154756",
                rating = 9,
                title = "Avengers: Infinity War",
                imdbIdUrl = "https://imdb.com/title/tt4154756",
                description = "It wasn't the best In the world",
                actors = "Robert Downey Jr., Chris Hemsworth",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_.jpg"
            )
        )


        movieReviewService.addReview(
            username = "mnmr1995",
            review = MovieReviewStarterDTO(
                imdbId = "tt9362722",
                rating = 7,
                title = "Spider-Man: Across the Spider-Verse",
                imdbIdUrl = "https://imdb.com/title/tt9362722",
                description = "Spider-Man: Into the Spider-Verse is probably my favorite Spider-Man movie. However, my feelings about Spider-Man: Across the Spider-Verse are currently glitching. On one hand, the action scenes are spectacular, and the animation is, once again, amazing. On the other hand, this movie is 2 hours and 20 minutes long and it feels like it. Plus, the visuals can be so kinetic that the film becomes busy & chaotic. This sequel offers some real drama, however it's not a complete story, and it ends on a cliffhanger. I think my rating will solidify when I watch Beyond the Spider-Verse and see how it all ends.",
                actors = "Shameik Moore, Hailee Steinfeld",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMzI0NmVkMjEtYmY4MS00ZDMxLTlkZmEtMzU4MDQxYTMzMjU2XkEyXkFqcGdeQXVyMzQ0MzA0NTM@._V1_.jpg"
            )
        )

        movieReviewService.addReview(
            username = "mnmr1995",
            review = MovieReviewStarterDTO(
                imdbId = "tt4633694",
                rating = 10,
                title = "Spider-Man: Into the Spider-Verse",
                imdbIdUrl = "https://imdb.com/title/tt4154756",
                description = "A movie worthy of Stan Lee's approval. Incredible animation, great story, great message. This film lives up to the hype and honestly surpassed my expectations. The only reason I think there are some negative reviews is because you can never please everyone. However, for the vast majority of viewers, I believe you'll find this film to be great. Highly recommend seeing it in theaters just for the amazing animation style.",
                actors = "Shameik Moore, Jake Johnson",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMjMwNDkxMTgzOF5BMl5BanBnXkFtZTgwNTkwNTQ3NjM@._V1_.jpg"
            )
        )

        movieReviewService.addReview(
            username = "mnmr1996",
            review = MovieReviewStarterDTO(
                imdbId = "tt4154756",
                rating = 9,
                title = "Avengers: Infinity War",
                imdbIdUrl = "https://imdb.com/title/tt4154756",
                description = "It was the best In the world",
                actors = "Robert Downey Jr., Chris Hemsworth",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_.jpg"
            )
        )




        movieReviewService.addReview(
            username = "mnmr1996",
            review = MovieReviewStarterDTO(
                imdbId = "tt6741278",
                rating = 7,
                title = "Invincible",
                imdbIdUrl = "https://imdb.com/title/tt6741278",
                description = "Omni Man was too evil for me",
                actors = "Steven Yeun, J.K. Simmons",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BN2Q1NWExNzEtM2M1Ny00ZDJhLWIwN2MtZGI5ZGI4MzBlYTQyXkEyXkFqcGdeQXVyOTYyMTY2NzQ@._V1_.jpg"
            )
        )


        movieReviewService.addReview(
            username = "mnmr1996",
            review = MovieReviewStarterDTO(
                imdbId = "tt0351283",
                rating = 8,
                title = "Madagascar",
                imdbIdUrl = "https://imdb.com/title/tt0351283",
                description = "This was the first movie to genuinely make me move it move it because it turns out I do in fact like to move it move it",
                actors = "Chris Rock, Ben Stiller",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BN2I5YzFlYWEtZjRhNy00ZmQzLWJhNTktZGIwYjFjODdmNDgxXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_.jpg"
            )
        )

        movieReviewService.addReview(
            username = "mnmr1996",
            review = MovieReviewStarterDTO(
                imdbId = "tt4633694",
                rating = 9,
                title = "Spider-Man: Into the Spider-Verse",
                imdbIdUrl = "https://imdb.com/title/tt4154756",
                description = "Even if you're not the biggest fan on Earth of animation, I'd urge you to give this film a try. It has such an incredibly broad appeal, it really does push the boundaries, and perhaps at the point in time that it was made, raise the bar on animation.",
                actors = "Shameik Moore, Jake Johnson",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMjMwNDkxMTgzOF5BMl5BanBnXkFtZTgwNTkwNTQ3NjM@._V1_.jpg"
            )
        )

        movieReviewService.addReview(
            username = "mnmr1996",
            review = MovieReviewStarterDTO(
                imdbId = "tt0110357",
                rating = 3,
                title = "The Lion King",
                imdbIdUrl = "https://imdb.com/title/tt0110357",
                description = "I don't like the fact that disney has turned this classic Movie into a live action",
                actors = "Matthew Broderick, Jeremy Irons",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BYTYxNGMyZTYtMjE3MS00MzNjLWFjNmYtMDk3N2FmM2JiM2M1XkEyXkFqcGdeQXVyNjY5NDU4NzI@._V1_.jpg"
            )
        )

        movieReviewService.addReview(
            username = "mnmr1996",
            review = MovieReviewStarterDTO(
                imdbId = "tt1517268",
                rating = 8,
                title = "Barbie",
                imdbIdUrl = "https://imdb.com/title/tt151726",
                description = "This movie offered some insightful commentary on the dynamics of man vs woman",
                actors = "Elmo Shropshire, Michele Lee",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BNjU3N2QxNzYtMjk1NC00MTc4LTk1NTQtMmUxNTljM2I0NDA5XkEyXkFqcGdeQXVyODE5NzE3OTE@._V1_.jpg"
            )
        )

        movieReviewService.addReview(
            username = "mnmr1996",
            review = MovieReviewStarterDTO(
                imdbId = "tt15398776",
                rating = 10,
                title = "Oppenheimer",
                imdbIdUrl = "https://imdb.com/title/tt15398776",
                description = "This movie is just... wow! I don't think I have ever felt like this watching a movie! Its like a blend of being sad but also scared! I read that Christopher Nolan said it kind of had themes of horror, and watching the movie i think I knew what he meant! Very few movies can make you feel quite like this one can!",
                actors = "Cillian Murphy, Emily Blunt",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY@._V1_.jpg"
            )
        )

        movieReviewService.addReview(
            username = "mnmr1997",
            review = MovieReviewStarterDTO(
                imdbId = "tt15398776",
                rating = 8,
                title = "Oppenheimer",
                imdbIdUrl = "https://imdb.com/title/tt15398776",
                description = "Oppenheimer is - with no doubt- going to be one of the best movies in the history. Amazing cinematography, Exceptional acting and terrifying Soundtracks.",
                actors = "Cillian Murphy, Emily Blunt",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY@._V1_.jpg"
            )
        )

        movieReviewService.addReview(
            username = "mnmr1997",
            review = MovieReviewStarterDTO(
                imdbId = "tt4154756",
                rating = 8,
                title = "Avengers: Infinity War",
                imdbIdUrl = "https://imdb.com/title/tt4154756",
                description = "The Send off was ok",
                actors = "Robert Downey Jr., Chris Hemsworth",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_.jpg"
            )
        )

        movieReviewService.addReview(
            username = "mnmr1998",
            review = MovieReviewStarterDTO(
                imdbId = "tt15398776",
                rating = 8,
                title = "Oppenheimer",
                imdbIdUrl = "https://imdb.com/title/tt15398776",
                description = "Good movie, but don't let Nolan fans fool you.",
                actors = "Cillian Murphy, Emily Blunt",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY@._V1_.jpg"
            )
        )

        movieReviewService.addReview(
            username = "mnmr1998",
            review = MovieReviewStarterDTO(
                imdbId = "tt4154756",
                rating = 5,
                title = "Avengers: Infinity War",
                imdbIdUrl = "https://imdb.com/title/tt4154756",
                description = "It wasn't the best In the world",
                actors = "Robert Downey Jr., Chris Hemsworth",
                imagePosterLink = "https://m.media-amazon.com/images/M/MV5BMjMxNjY2MDU1OV5BMl5BanBnXkFtZTgwNzY1MTUwNTM@._V1_.jpg"
            )
        )
    }


}