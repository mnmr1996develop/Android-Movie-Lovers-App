package com.michaelrichards.movieloversapp.network

import com.michaelrichards.movieloversapp.dtos.SearchDataDTO
import com.michaelrichards.movieloversapp.dtos.UserDataDTO
import com.michaelrichards.movieloversapp.dtos.UserProfileDTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface UserDataAPI {

    @GET("users")
    suspend fun getUserBasicDetails(@Header("Authorization") token: String): UserDataDTO

    @GET("users/user/{username}")
    suspend fun getUserProfileDetails(@Header("Authorization") token: String, @Path("username") username: String): UserProfileDTO

    @GET("users/following")
    suspend fun getUserFollowing(@Header("Authorization") token: String, @Query("pageNumber") pageNumber: Int): List<UserDataDTO>

    @GET("users/search")
    suspend fun searchUser(@Header("Authorization") token: String, @Query("query") query: String): List<SearchDataDTO>

    @GET("users/followers")
    suspend fun getUserFollowers(@Header("Authorization") token: String, @Query("pageNumber") pageNumber: Int): List<UserDataDTO>

    @POST("users/follow")
    suspend fun follow(@Header("Authorization") token: String,@Query("username") username: String): UserDataDTO

    @DELETE("users/follow")
    suspend fun unfollow(@Header("Authorization") token: String,@Query("username") username: String): Unit


}