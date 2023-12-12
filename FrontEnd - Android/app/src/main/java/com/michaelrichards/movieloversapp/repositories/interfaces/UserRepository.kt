package com.michaelrichards.movieloversapp.repositories.interfaces

import com.michaelrichards.movieloversapp.data.DataOrException
import com.michaelrichards.movieloversapp.dtos.BasicUserDataDTO
import com.michaelrichards.movieloversapp.dtos.UserProfileDTO
import com.michaelrichards.movieloversapp.repositories.results.ApiState

interface UserRepository {
    suspend fun getUserDetails(): DataOrException<UserProfileDTO, Boolean, Exception>

    suspend fun getUserFollowing(pageNumber: Int): Result<List<UserProfileDTO>>

    suspend fun getUserFollowers(pageNumber: Int): Result<List<UserProfileDTO>>

    suspend fun searchUser(userName: String): Result<List<UserProfileDTO>>

    suspend fun follow(username: String): Result<BasicUserDataDTO>

    suspend fun unfollow(username: String): Result<Unit>

    suspend fun getUserProfile(username: String): ApiState<UserProfileDTO>
}