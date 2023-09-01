package com.MichaelRichards.MovieLovers.controllers

import com.MichaelRichards.MovieLovers.dtos.JwtAuthenticationResponse
import com.MichaelRichards.MovieLovers.dtos.SignUpRequest
import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.models.Role
import com.MichaelRichards.MovieLovers.services.AuthenticationService
import com.MichaelRichards.MovieLovers.services.EnthusiastService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI


@RestController
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/v1/admins")
class AdminController (
    private val enthusiastService: EnthusiastService,
    private val authenticationService: AuthenticationService
){

    companion object{
        private const val BASE_PATH =  "/api/v1/admins"
    }

    @GetMapping("/users")
    fun getAllUsers(): ResponseEntity<List<Enthusiast>> = ResponseEntity.ok(enthusiastService.getAllUsers())

    @GetMapping("/users/{username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<Enthusiast> = ResponseEntity.ok(enthusiastService.findByUsername(username))

    @PostMapping
    fun registerAdmin(@RequestBody signUpRequest: SignUpRequest): ResponseEntity<JwtAuthenticationResponse> {
        val uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(BASE_PATH).toUriString())
        return ResponseEntity.created(uri).body(authenticationService.userRegistration(signUpRequest, Role.ROLE_ADMIN))
    }

}