package com.michaelrichards.movieloversapp.screens.UserDetailsScreen

import androidx.lifecycle.ViewModel
import com.michaelrichards.movieloversapp.dtos.UserProfileDTO
import com.michaelrichards.movieloversapp.repositories.interfaces.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel(){


    suspend fun checkValidUsername(username: String): UserProfileDTO {
        TODO("check if the page is valid")
    }

}