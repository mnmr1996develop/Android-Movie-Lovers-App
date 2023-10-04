package com.michaelrichards.movieloversapp.repositories.implementaions



import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.michaelrichards.movieloversapp.dtos.ErrorResponse
import com.michaelrichards.movieloversapp.dtos.SignInRequest
import com.michaelrichards.movieloversapp.dtos.SignUpRequest
import com.michaelrichards.movieloversapp.model.JwtToken
import com.michaelrichards.movieloversapp.network.AuthAPI
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository
import com.michaelrichards.movieloversapp.repositories.results.AuthResult
import retrofit2.HttpException

class AuthRepositoryImpl (
    private val api: AuthAPI,
    private val prefs: SharedPreferences
) : AuthRepository {

    private val TAG = "AuthRepositoryImpl"
    override suspend fun login(signInRequest: SignInRequest): AuthResult<Unit> {

        return try {
            val request = api.login(signInRequest)
            prefs.edit()
                .putString("jwt", request.accessToken)
                .apply()
            AuthResult.Authorized()
        }catch (e: HttpException){
             if (e.code() == 400){
                 AuthResult.UnAuthorized()
             }else{
                 AuthResult.UnknownError()
             }
        }catch (e: Exception){
                AuthResult.UnknownError()
        }
    }


    override suspend fun register(
        signUpRequest: SignUpRequest
    ) : AuthResult<String> {
        return try {
            val request = api.register(request = signUpRequest)
            prefs.edit()
                .putString("jwt", request.accessToken)
                .apply()
            AuthResult.Authorized()
        }catch (e: HttpException){
            if (e.code() == 401 || e.code() == 403){
                AuthResult.UnAuthorized("UnAuthorized Access")
            }
            else if (e.code() == 400){
                val gson = Gson()
                val type = object : TypeToken<ErrorResponse>() {}.type
                val errorResponse: ErrorResponse? = gson.fromJson(e.response()?.errorBody()!!.charStream(), type)
                AuthResult.BadRequest(data = errorResponse?.message)
            }
            else{
                AuthResult.UnknownError()
            }
        }catch (e: Exception){
            AuthResult.UnknownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null)
            if (token == null){
                AuthResult.UnAuthorized()
            }
            else {
                val jwtToken = JwtToken(token)
                api.authenticate(jwtToken)
                AuthResult.Authorized()
            }
        }catch (e: HttpException){
            if (e.code() == 401 || e.code() == 403){
                AuthResult.UnAuthorized()
            }else{
                AuthResult.UnknownError()
            }
        }catch (e: Exception){
            AuthResult.UnknownError()
        }
    }

    override suspend fun logout(): AuthResult<Unit> {
        val token = prefs.getString("jwt", null)

        return try {
            if (token == null) {
                AuthResult.UnAuthorized()
            } else {
                api.logout(token)
                prefs.edit().remove("jwt").apply()
                AuthResult.UnAuthorized()
            }
        } catch (e: Exception){
            AuthResult.UnknownError()
        }

    }


}