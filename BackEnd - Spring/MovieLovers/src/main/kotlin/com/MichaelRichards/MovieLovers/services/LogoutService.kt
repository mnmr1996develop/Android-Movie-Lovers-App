package com.MichaelRichards.MovieLovers.services

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service

@Service
class LogoutService(private val tokenService: TokenService): LogoutHandler {
    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val authHeader = request?.getHeader("Authorization")
        if (authHeader.isNullOrEmpty() || !authHeader.startsWith("Bearer ")) {

            return
        }
        val jwt: String = authHeader.substring(7)
        val storedToken = tokenService.findTokenByName(jwt)
        storedToken.isNotExpired = false
        storedToken.isNotRevoked = false
        tokenService.saveToken(storedToken)

    }
}