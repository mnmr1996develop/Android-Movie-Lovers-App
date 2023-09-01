package com.MichaelRichards.MovieLovers.filters

import com.MichaelRichards.MovieLovers.repositories.TokenRepository
import com.MichaelRichards.MovieLovers.services.EnthusiastService
import com.MichaelRichards.MovieLovers.services.JwtService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter (
    private val jwtService: JwtService,
    private val enthusiastService: EnthusiastService,
    private val tokenRepository: TokenRepository
): OncePerRequestFilter(){

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")
        if (authHeader.isNullOrEmpty() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }
        val jwt: String = authHeader.substring(7)
        val username: String = jwtService.extractUsername(jwt)

        val token = tokenRepository.findByToken(jwt)

        if (token == null){
            filterChain.doFilter(request, response)
            return
        }

        if (username.isNotEmpty() && (SecurityContextHolder.getContext().authentication == null) && token.isValid()) {
            val userDetails = enthusiastService.loadUserByUsername(username)
            if (jwtService.isTokenValid(jwt, userDetails)) run {
                val context: SecurityContext = SecurityContextHolder.createEmptyContext()
                val authToken = UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
                authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                context.authentication = authToken
                SecurityContextHolder.setContext(context)
            }
        }
        filterChain.doFilter(request, response)
    }

}
