package com.MichaelRichards.MovieLovers.controllers

import com.MichaelRichards.MovieLovers.dtos.JwtAuthenticationResponse
import com.MichaelRichards.MovieLovers.dtos.SignInRequest
import com.MichaelRichards.MovieLovers.dtos.SignUpRequest
import com.MichaelRichards.MovieLovers.dtos.TokenRequest
import com.MichaelRichards.MovieLovers.services.AuthenticationService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(private val authenticationService: AuthenticationService) {

    companion object{
        private const val BASE_PATH =  "api/v1/auth/"
    }

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignUpRequest): ResponseEntity<JwtAuthenticationResponse> {
        val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("$BASE_PATH/signup").toUriString())
        return ResponseEntity.created(uri).body(authenticationService.userRegistration(request))
    }

    @PostMapping("/authenticate")
    fun authenticate(@RequestBody jwtToken: TokenRequest): ResponseEntity<Boolean> {
        return if (authenticationService.authenticateToken(jwtToken.jwtToken.removePrefix("Bearer ")))
                    ResponseEntity.ok().body(true)
        else ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false)
    }

    @PostMapping("/login")
    fun login(@RequestBody request: SignInRequest): ResponseEntity<JwtAuthenticationResponse> {
        return ResponseEntity.ok().body(authenticationService.login(request))
    }

    @PostMapping("/refresh-token")
    fun refreshToken(request: HttpServletRequest, response: HttpServletResponse){
            authenticationService.refreshToken(request, response)
    }
}