package com.MichaelRichards.MovieLovers.controllers

import com.MichaelRichards.MovieLovers.dtos.JwtAuthenticationResponse
import com.MichaelRichards.MovieLovers.dtos.SignInRequest
import com.MichaelRichards.MovieLovers.dtos.SignUpRequest
import com.MichaelRichards.MovieLovers.services.AuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI

@RestController
@RequestMapping("/api/v1/auth")
class AuthenticationController(private val authenticationService: AuthenticationService) {

    companion object{
        private const val BASE_PATH =  "api/v1/admins/"
    }

    @PostMapping("/signup")
    fun signup(@RequestBody request: SignUpRequest): ResponseEntity<JwtAuthenticationResponse> {
        val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("$BASE_PATH/signup").toUriString())
        return ResponseEntity.created(uri).body(authenticationService.userRegistration(request))
    }

    @PostMapping("/login")
    fun login(@RequestBody request: SignInRequest): ResponseEntity<JwtAuthenticationResponse> {
        return ResponseEntity.ok().body(authenticationService.login(request))
    }
}