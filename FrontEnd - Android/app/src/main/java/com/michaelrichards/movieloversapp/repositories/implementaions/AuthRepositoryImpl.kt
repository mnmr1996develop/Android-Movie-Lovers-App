package com.michaelrichards.movieloversapp.repositories.implementaions



import android.content.SharedPreferences
import android.util.Log
import com.michaelrichards.movieloversapp.dtos.SignInRequest
import com.michaelrichards.movieloversapp.dtos.SignUpRequest
import com.michaelrichards.movieloversapp.network.AuthAPI
import com.michaelrichards.movieloversapp.repositories.auth.AuthResult
import com.michaelrichards.movieloversapp.repositories.interfaces.AuthRepository
import retrofit2.HttpException
import java.lang.Exception

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
    ) : AuthResult<Unit> {
        return try {
            val response = api.register(request = signUpRequest)
            prefs.edit()
                .putString("jwt", response.accessToken)
                .apply()
            AuthResult.Authorized()
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

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = prefs.getString("jwt", null)
            if (token == null){
                AuthResult.UnAuthorized()
            }
            else {
                //api.authenticate("Bearer $token")
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
                api.logout("Bearer $token")
                prefs.edit().remove("jwt").apply()
                AuthResult.UnAuthorized()
            }
        } catch (e: Exception){
            AuthResult.UnknownError()
        }

    }


}