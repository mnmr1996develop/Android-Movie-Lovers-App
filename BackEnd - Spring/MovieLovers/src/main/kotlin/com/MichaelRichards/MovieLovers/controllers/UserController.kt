package com.MichaelRichards.MovieLovers.controllers

import com.MichaelRichards.MovieLovers.models.Enthusiast
import com.MichaelRichards.MovieLovers.services.EnthusiastService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val enthusiastService: EnthusiastService,
) {

    @GetMapping
    fun getUserDetails(@RequestHeader("Authorization") bearerToken: String): ResponseEntity<Enthusiast>{
        val enthusiast = enthusiastService.getUserByBearerToken(bearerToken)
        return ResponseEntity.ok().body(enthusiast)
    }
}