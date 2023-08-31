package com.MichaelRichards.MovieLovers.controllers

import com.MichaelRichards.MovieLovers.dtos.JwtAuthenticationResponse
import com.MichaelRichards.MovieLovers.dtos.SignInRequest
import com.MichaelRichards.MovieLovers.dtos.SignUpRequest
import com.MichaelRichards.MovieLovers.services.AuthenticationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(private val authenticationService: AuthenticationService) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignUpRequest): JwtAuthenticationResponse {
        return authenticationService.userRegistration(request)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: SignInRequest): JwtAuthenticationResponse {
        return authenticationService.login(request)
    }
}