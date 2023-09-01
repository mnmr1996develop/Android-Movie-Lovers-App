package com.MichaelRichards.MovieLovers.services

import com.MichaelRichards.MovieLovers.dtos.JwtAuthenticationResponse
import com.MichaelRichards.MovieLovers.dtos.SignInRequest
import com.MichaelRichards.MovieLovers.dtos.SignUpRequest
import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.Role
import com.MichaelRichards.MovieLovers.models.Token
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthenticationService(
    private val enthusiastService: EnthusiastService,
    private val tokenService: TokenService,
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
        val jwtToken = saveUserToken(user)
        return JwtAuthenticationResponse(accessToken = jwtToken)
    }


    fun userRegistration(request: SignUpRequest, role: Role = Role.ROLE_USER): JwtAuthenticationResponse {
        val newUser = Enthusiast(
            username = request.username,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            firstName = request.firstName,
            lastName = request.lastName,
            birthday = request.birthday,
            role = role
        )
        val user = enthusiastService.save(newUser)
        val jwtToken = saveUserToken(user)
        return JwtAuthenticationResponse(accessToken = jwtToken)
    }

    private fun saveUserToken(enthusiast: Enthusiast) : String{
        revokeAllUserTokens(enthusiast)
        val jwtToken = jwtService.generateToken(userDetails = enthusiast)
        val token = Token(
            enthusiast = enthusiast,
            token = jwtToken
        )
       tokenService.saveToken(token)
        return jwtToken
    }

    private fun revokeAllUserTokens(enthusiast: Enthusiast){
        val validUserTokens = enthusiast.id?.let { tokenService.findAllValidTokensByUser(it) }
        if (validUserTokens != null) {
            if (validUserTokens.isEmpty())return
            validUserTokens.forEach { token ->
                token.isNotRevoked = false
                token.isNotExpired = false
            }
            tokenService.saveAll(validUserTokens)
        }

    }
}
