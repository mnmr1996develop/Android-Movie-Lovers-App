package com.michaelrichards.movieloversapp.repositories.interfaces

import com.michaelrichards.movieloversapp.data.DataOrException
import com.michaelrichards.movieloversapp.dtos.SearchDataDTO
import com.michaelrichards.movieloversapp.dtos.UserDataDTO
import com.michaelrichards.movieloversapp.dtos.UserProfileDTO

interface UserRepository {
    suspend fun getBasicUserDetails(): DataOrException<UserDataDTO, Boolean, Exception>

    suspend fun getUserFollowing(pageNumber: Int): Result<List<UserDataDTO>>

    suspend fun getUserFollowers(pageNumber: Int): Result<List<UserDataDTO>>

    suspend fun searchUser(userName: String): Result<List<SearchDataDTO>>

    suspend fun follow(username: String): Result<UserDataDTO>

    suspend fun unfollow(username: String): Result<Unit>

    suspend fun getUserProfile(username: String): Result<UserProfileDTO>
}