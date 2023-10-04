package com.michaelrichards.movieloversapp.repositories.interfaces

import com.michaelrichards.movieloversapp.data.DataOrException
import com.michaelrichards.movieloversapp.dtos.UserDataDTO
import com.michaelrichards.movieloversapp.repositories.results.DataResults

interface UserRepository {
    suspend fun getBasicUserDetails(): DataOrException<UserDataDTO, Boolean, Exception>
}