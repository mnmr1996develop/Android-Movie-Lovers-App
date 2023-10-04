package com.michaelrichards.movieloversapp.repositories.implementaions

import android.content.SharedPreferences
import com.google.gson.Gson
import com.michaelrichards.movieloversapp.data.DataOrException
import com.michaelrichards.movieloversapp.dtos.UserDataDTO
import com.michaelrichards.movieloversapp.network.UserDataAPI
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository
import com.michaelrichards.movieloversapp.repositories.results.DataResults
import retrofit2.HttpException

class UserRepositoryImpl (
    private val api: UserDataAPI,
    private val prefs: SharedPreferences
): UserRepository {

    override suspend fun getBasicUserDetails(): DataOrException<UserDataDTO, Boolean, Exception> {
        val dataOrException = DataOrException<UserDataDTO, Boolean, Exception>()
        val jwtToken = prefs.getString("jwt", null)
        if (jwtToken == null){
            dataOrException.e = Exception()
            return dataOrException
        }
        try {
                dataOrException.loading = true
                dataOrException.data = api.getUserBasicDetails("Bearer $jwtToken")
                val gson = Gson()
                val json = gson.toJson(dataOrException.data)
                prefs.edit().putString("basic_user_data", json).apply()
                dataOrException.loading = false
        }catch (e: Exception){
            dataOrException.e = e
        }
        return dataOrException
    }
}