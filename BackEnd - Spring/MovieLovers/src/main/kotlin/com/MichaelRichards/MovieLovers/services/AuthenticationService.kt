package com.MichaelRichards.MovieLovers.services

import com.MichaelRichards.MovieLovers.dtos.JwtAuthenticationResponse
import com.MichaelRichards.MovieLovers.dtos.SignInRequest
import com.MichaelRichards.MovieLovers.dtos.SignUpRequest
import com.MichaelRichards.MovieLovers.models.Enthusiast
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val enthusiastService: EnthusiastService,

    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val authenticationManager: AuthenticationManager
) {

    fun login(request: SignInRequest): JwtAuthenticationResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                request.username,
                request.password
            )
        )
        val user = enthusiastService.findByUsername(request.username)
        val jwtToken = jwtService.generateToken(user)
        return JwtAuthenticationResponse(accessToken = jwtToken)
    }


    fun userRegistration(request: SignUpRequest): JwtAuthenticationResponse {


        val newUser = Enthusiast(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            firstName = request.firstName,
            lastName = request.lastName,
            birthday = request.birthday
        )
        val user = enthusiastService.save(newUser)
        val jwtToken = jwtService.generateToken(userDetails = user)

        return JwtAuthenticationResponse(accessToken = jwtToken)
    }
}
