package com.MichaelRichards.MovieLovers.services

import com.MichaelRichards.MovieLovers.dtos.BasicUserDataDTO
import com.MichaelRichards.MovieLovers.dtos.ProfileDataDTO
import com.MichaelRichards.MovieLovers.exceptions.CustomExceptions
import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.Followers
import com.MichaelRichards.MovieLovers.repositories.FollowRepository
import com.MichaelRichards.MovieLovers.utils.isPageNumberValid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional
@Service
class FollowService(
    private val enthusiastService: EnthusiastService,
    private val followRepository: FollowRepository
){


    fun follow(bearerToken: String, username: String): BasicUserDataDTO {
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        val followee = enthusiastService.findByUsername(username)

        if (followRepository.existsByFollowerAndFollowee(user, followee) || user.username == followee.username)
            throw CustomExceptions.AlreadyFollowing(user.username ,username)

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

    fun unfollow(bearerToken: String, username: String) {
        val user : Enthusiast = enthusiastService.getUserByBearerToken(bearerToken)
        val userToUnfollow = enthusiastService.findByUsername(username)
        val followModel = followRepository.findByFollowerAndFollowee(user, userToUnfollow) ?: throw CustomExceptions.CannotFindFollowing(user.username, userToUnfollow.username)
        followModel.follower.followers.remove(followModel)
        followModel.followee.following.remove(followModel)
        followRepository.delete(followModel)
    }

    fun getMyFollowersPaged(bearerToken: String, pageNumber: Int): List<ProfileDataDTO> {
        val user = enthusiastService.getUserByBearerToken(bearerToken)

        isPageNumberValid(pageNumber)

        val pageable: Pageable = PageRequest.of(pageNumber, 20)

        return followRepository.findByFolloweePageable(user,pageable).map {follower ->
            ProfileDataDTO(
                firstName = follower.follower.firstName,
                lastName = follower.follower.lastName,
                email = follower.follower.email,
                username = follower.follower.username,
                birthday = follower.follower.birthday,
                followers = follower.follower.followers.size,
                following = follower.follower.following.size,
                totalReviews = follower.follower.movieReviews.size,
                amIFollowing = followRepository.existsByFollowerAndFollowee(user, follower.follower)

            )
        }
    }

    fun getMyFollowers(bearerToken: String): List<BasicUserDataDTO> {
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        return followRepository.findByFollowee(user).map {follower ->
            BasicUserDataDTO(
                firstName = follower.follower.firstName,
                lastName = follower.follower.lastName,
                email = follower.follower.email,
                username = follower.follower.username,
                birthday = follower.follower.birthday
            )
        }
    }

    fun getUserIFollowPaged(bearerToken: String, pageNumber: Int): List<ProfileDataDTO> {
        val user = enthusiastService.getUserByBearerToken(bearerToken)

        isPageNumberValid(pageNumber)

        val pageable: Pageable = PageRequest.of(pageNumber, 20)

        return followRepository.findByFollowerPageable(user, pageable).map { usersIFollow ->
            ProfileDataDTO(
                firstName = usersIFollow.followee.firstName,
                lastName = usersIFollow.followee.lastName,
                username = usersIFollow.followee.username,
                email = usersIFollow.followee.email,
                birthday = usersIFollow.followee.birthday,
                followers = usersIFollow.followee.followers.size,
                following = usersIFollow.followee.following.size,
                totalReviews = usersIFollow.followee.movieReviews.size,
                amIFollowing = true
            )
        }
    }

    fun getUserIFollow(bearerToken: String): List<ProfileDataDTO> {
        val user = enthusiastService.getUserByBearerToken(bearerToken)
        return followRepository.findByFollower(user).map { usersIFollow ->
            ProfileDataDTO(
                firstName = usersIFollow.followee.firstName,
                lastName = usersIFollow.followee.lastName,
                username = usersIFollow.followee.username,
                email = usersIFollow.followee.email,
                birthday = usersIFollow.followee.birthday,
                followers = usersIFollow.followee.followers.size,
                following = usersIFollow.followee.following.size,
                totalReviews = usersIFollow.followee.movieReviews.size,
            )
        }
    }



}