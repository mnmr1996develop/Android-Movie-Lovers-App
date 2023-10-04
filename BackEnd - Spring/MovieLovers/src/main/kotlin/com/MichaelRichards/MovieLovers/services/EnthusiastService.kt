package com.MichaelRichards.MovieLovers.services

import com.MichaelRichards.MovieLovers.dtos.BasicUserDataDTO
import com.MichaelRichards.MovieLovers.exceptions.CustomExceptions
import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.Followers
import com.MichaelRichards.MovieLovers.repositories.EnthusiastRepository
import com.MichaelRichards.MovieLovers.repositories.FollowRepository
import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
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

    fun findByUsername(username: String): Enthusiast = nullableFindByUsername(username) ?: throw UsernameNotFoundException("$username not found")

    fun nullableFindByUsername(username: String): Enthusiast? = enthusiastRepository.findByUsername(username)
    fun nullableFindByEmail(email: String): Enthusiast? = enthusiastRepository.findByEmail(email)

    fun getUserByBearerToken(bearerToken: String): Enthusiast = findByUsername(jwtService.extractUsername( bearerToken.substring(7)))

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

    fun getAllUsers() : List<Enthusiast> = enthusiastRepository.findAll()
    fun getMyFollowers(bearerToken: String, pageNumber: Int): List<BasicUserDataDTO> {
        val user  = getUserByBearerToken(bearerToken)
        val followers: MutableList<BasicUserDataDTO> = mutableListOf()

        if(pageNumber<1){
            throw IndexOutOfBoundsException()
        }

        val pageable: Pageable = PageRequest.of(pageNumber-1,20)

        for (myFollowing in followRepository.findByFollowee(user,pageable)){
            val userDetails = BasicUserDataDTO(
                birthday = myFollowing.follower.birthday,
                username = myFollowing.follower.username,
                email = myFollowing.follower.email,
                firstName = myFollowing.follower.firstName,
                lastName = myFollowing.follower.lastName
            )
            followers.add(userDetails)
        }
        return followers.toList()
    }

    fun getUserIFollow(bearerToken: String, pageNumber: Int): List<BasicUserDataDTO> {
        val user  = getUserByBearerToken(bearerToken)
        val followingList: MutableList<BasicUserDataDTO> = mutableListOf()

        if(pageNumber<1){
            throw IndexOutOfBoundsException("")
        }

        val pageable: Pageable = PageRequest.of(pageNumber-1,20)

        for (usersIFollow in followRepository.findByFollower(user, pageable)){
            val userDetails = BasicUserDataDTO(
                birthday = usersIFollow.followee.birthday,
                username = usersIFollow.followee.username,
                email = usersIFollow.followee.email,
                firstName = usersIFollow.followee.firstName,
                lastName = usersIFollow.followee.lastName
            )
            followingList.add(userDetails)
        }

        return followingList.toList()
    }


    @Transactional
    fun follow(bearerToken: String, username: String): BasicUserDataDTO {
        val user = getUserByBearerToken(bearerToken)
        val followee = findByUsername(username)


        if (followRepository.existsByFollowerAndFollowee(user, followee) || user.username == followee.username)
            throw CustomExceptions.AlreadyFollowing("${user.username} already follows $username")


        val follow = Followers(
            follower = user,
            followee = followee
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


}