package com.MichaelRichards.MovieLovers.services

import com.MichaelRichards.MovieLovers.dtos.JwtAuthenticationResponse
import com.MichaelRichards.MovieLovers.dtos.SignInRequest
import com.MichaelRichards.MovieLovers.dtos.SignUpRequest
import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.Role
import com.MichaelRichards.MovieLovers.models.Token
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.coyote.http11.Constants.a
import org.springframework.http.HttpHeaders
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
        val (jwtToken, jwtRefresh) = saveUserToken(user)
        return JwtAuthenticationResponse(accessToken = jwtToken, refreshToken = jwtRefresh)
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
        val (jwtToken, jwtRefresh) = saveUserToken(user)
        return JwtAuthenticationResponse(accessToken = jwtToken, refreshToken = jwtRefresh)
    }

    private fun saveUserToken(enthusiast: Enthusiast) : Pair<String, String>{
        revokeAllUserTokens(enthusiast)
        val jwtToken = jwtService.generateToken(userDetails = enthusiast)
        val jwtRefreshToken = jwtService.generateRefreshToken(enthusiast)
        val token = Token(
            enthusiast = enthusiast,
            token = jwtToken
        )
       tokenService.saveToken(token)
        return Pair(jwtToken, jwtRefreshToken)
    }

    private fun saveUserTokenWithoutRefresh(enthusiast: Enthusiast, jwtToken: String){
        revokeAllUserTokens(enthusiast)
        val token = Token(
            enthusiast = enthusiast,
            token = jwtToken
        )
        tokenService.saveToken(token)
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

    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse){
        val authHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (authHeader.isNullOrEmpty() || !authHeader.startsWith("Bearer ")) {
            return
        }
        val refreshToken: String = authHeader.substring(7)
        val username: String = jwtService.extractUsername(refreshToken)

        if (username.isNotEmpty()) {
            val userDetails = enthusiastService.findByUsername(username)
            if (jwtService.isTokenValid(refreshToken, userDetails)) run {
                val accessToken = jwtService.generateToken(userDetails = userDetails)
                saveUserTokenWithoutRefresh(userDetails, accessToken)
                val authResponse = JwtAuthenticationResponse(
                    accessToken = accessToken,
                    refreshToken = refreshToken
                )
                val mapper = ObjectMapper().writeValue(response.outputStream, authResponse)
            }
        }
    }

    fun authenticateToken(jwtToken: String): Boolean  = tokenService.findTokenByName(jwtToken).isValid()

}
