package com.MichaelRichards.MovieLovers.services

import com.MichaelRichards.MovieLovers.dtos.BasicSearchUserDataDTO
import com.MichaelRichards.MovieLovers.dtos.BasicUserDataDTO
import com.MichaelRichards.MovieLovers.dtos.ProfileDataDTO
import com.MichaelRichards.MovieLovers.exceptions.CustomExceptions
import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.Followers
import com.MichaelRichards.MovieLovers.repositories.EnthusiastRepository
import com.MichaelRichards.MovieLovers.repositories.FollowRepository
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EnthusiastService(
    private val enthusiastRepository: EnthusiastRepository,
    private val jwtService: JwtService,
    private val followRepository: FollowRepository
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

    fun findByUsername(username: String): Enthusiast =
        nullableFindByUsername(username) ?: throw UsernameNotFoundException("$username not found")

    fun getByUsername(username: String, bearerToken: String = ""): ProfileDataDTO {
        val user = findByUsername(username)
        return if (bearerToken.length > 1){

            val me = getUserByBearerToken(bearerToken)

            ProfileDataDTO(
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                username = user.username,
                birthday = user.birthday,
                following = user.following.size,
                followers = user.followers.size,
                totalReviews = user.movieReviews.size,
                amIFollowing = followRepository.existsByFollowerAndFollowee(me, user),
                followingUser = followRepository.existsByFollowerAndFollowee(user, me)

            )
        } else ProfileDataDTO(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            username = user.username,
            birthday = user.birthday,
            totalReviews = user.movieReviews.size,
            following = user.following.size,
            followers = user.followers.size,
        )
    }

    fun nullableFindByUsername(username: String): Enthusiast? = enthusiastRepository.findByUsername(username)
    fun nullableFindByEmail(email: String): Enthusiast? = enthusiastRepository.findByEmail(email)

    fun getUserByBearerToken(bearerToken: String): Enthusiast =
        findByUsername(jwtService.extractUsername(bearerToken.substring(7)))

    fun getBasicUserDataByBearerToken(bearerToken: String): BasicUserDataDTO {
        val jwt: String = bearerToken.substring(7)
        val user = findByUsername(jwtService.extractUsername(jwt))
        return BasicUserDataDTO(
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            username = user.username,
            birthday = user.birthday
        )
    }

    fun getAllUsers(): List<Enthusiast> = enthusiastRepository.findAll()


    @Transactional
    fun followForSeedData(follower: String, following: String): BasicUserDataDTO {
        val user: Enthusiast = findByUsername(follower)
        val followee: Enthusiast = findByUsername(following)

        if (followRepository.existsByFollowerAndFollowee(user, followee) || user.username == followee.username)
            throw CustomExceptions.AlreadyFollowing(user.username, followee.username)

        val follow = Followers(
            follower = user,
            followee = followee,
            localDateTime = LocalDateTime.now()
        )

        user.following.add(follow)
        followee.followers.add(follow)
        followRepository.save(follow)

        return BasicUserDataDTO(
            firstName = followee.firstName,
            lastName = followee.lastName,
            username = followee.username,
            birthday = followee.birthday,
            email = followee.email
        )
    }

    fun searchUser(queryCaller: String, username: String): List<BasicSearchUserDataDTO> {

        val me = getUserByBearerToken(queryCaller)

        return enthusiastRepository.searchUsers(username).map { enthusiast ->
                BasicSearchUserDataDTO(
                    firstName = enthusiast.firstName,
                    lastName = enthusiast.lastName,
                    email = enthusiast.email,
                    username = enthusiast.username,
                    birthday = enthusiast.birthday,
                    following = followRepository.existsByFollowerAndFollowee(me, enthusiast),
                    follower = followRepository.existsByFollowerAndFollowee(enthusiast, me)
                )
            }
    }
}