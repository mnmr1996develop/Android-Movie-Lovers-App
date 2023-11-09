package com.michaelrichards.movieloversapp.repositories.implementaions

import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.michaelrichards.movieloversapp.data.DataOrException
import com.michaelrichards.movieloversapp.dtos.SearchDataDTO
import com.michaelrichards.movieloversapp.dtos.UserDataDTO
import com.michaelrichards.movieloversapp.dtos.UserProfileDTO
import com.michaelrichards.movieloversapp.network.UserDataAPI
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository

private const val TAG = "UserRepositoryImpl"
class UserRepositoryImpl (
    private val api: UserDataAPI,
    private val prefs: SharedPreferences
): UserRepository {

    override suspend fun getBasicUserDetails(): DataOrException<UserDataDTO, Boolean, Exception> {
        val dataOrException = DataOrException<UserDataDTO, Boolean, Exception>()
        val jwtToken = prefs.getString("jwt", null)
        Log.i(TAG, "getBasicUserDetails: $jwtToken")
        if (jwtToken == null){
            dataOrException.e = Exception()
            return dataOrException
        }
        try {
                dataOrException.loading = true
                dataOrException.data = api.getUserBasicDetails(jwtToken)
                val gson = Gson()
                val json = gson.toJson(dataOrException.data)
                prefs.edit().putString("basic_user_data", json).apply()
            Log.i(TAG, "getBasicUserDetails: $json")
                dataOrException.loading = false
        }catch (e: Exception){
            dataOrException.e = e
        }
        return dataOrException
    }

    override suspend fun getUserFollowing(pageNumber: Int): Result<List<UserDataDTO>> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return Result.failure(exception = Throwable("Something went wrong"))

        return try {
            val listOfUsers = api.getUserFollowing(jwtToken, pageNumber)
            Result.success( listOfUsers )
        }catch (e: Exception){
            Result.failure(exception = e.fillInStackTrace())
        }
    }


    override suspend fun getUserFollowers(pageNumber: Int): Result<List<UserDataDTO>> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return Result.failure(exception = Throwable("Something went wrong"))

        return try {
            val listOfUsers = api.getUserFollowers(jwtToken,pageNumber)
            Result.success( listOfUsers)
        }catch (e: Exception){
            Result.failure(exception = e)
        }
    }

    override suspend fun searchUser(userName: String): Result<List<SearchDataDTO>> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return Result.failure(exception = Throwable("Something went wrong"))

        return try {
            val listOfUsers = api.searchUser(jwtToken, userName)
            Result.success( listOfUsers)
        }catch (e: Exception){
            Result.failure(exception = e)
        }
    }

    override suspend fun follow(username: String): Result<UserDataDTO> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return Result.failure(exception = Throwable("Something went wrong"))

        if (username.isEmpty()){
            return Result.failure(Throwable("Something went wrong"))
        }

        return try {
            val user = api.follow(jwtToken, username)
            Result.success(user)
        }catch (e: Exception){
            Result.failure(exception = e)
        }
    }

    override suspend fun unfollow(username: String): Result<Unit> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return Result.failure(exception = Throwable("Something went wrong"))

        if (username.isEmpty()){
            return Result.failure(Throwable("Something went wrong"))
        }

        return try {
            val user = api.unfollow(jwtToken, username)
            Result.success(user)
        }catch (e: Exception){
            Result.failure(exception = e)
        }
    }

    override suspend fun getUserProfile(username: String): Result<UserProfileDTO> {
        val jwtToken = prefs.getString("jwt", null)
            ?: return Result.failure(exception = Throwable("Something went wrong"))

        if (username.isEmpty()){
            return Result.failure(Throwable("Something went wrong"))
        }

        return try {
            val user = api.getUserProfileDetails(jwtToken,username)
            Result.success(user)
        }catch (e: Exception){
            Result.failure(exception = e)
        }

    }

}