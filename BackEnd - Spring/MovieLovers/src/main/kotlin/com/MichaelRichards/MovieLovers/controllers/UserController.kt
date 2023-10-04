package com.MichaelRichards.MovieLovers.controllers

import com.MichaelRichards.MovieLovers.dtos.BasicUserDataDTO
import com.MichaelRichards.MovieLovers.services.EnthusiastService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

private const val BASE_PATH = "api/v1/users"

@RestController
@RequestMapping(BASE_PATH)
class UserController(
    private val enthusiastService: EnthusiastService,
) {

    @GetMapping
    fun getUserDetails(@RequestHeader("Authorization") bearerToken: String): ResponseEntity<BasicUserDataDTO> {
        val enthusiast = enthusiastService.getBasicUserDataByBearerToken(bearerToken)
        return ResponseEntity.ok().body(enthusiast)
    }

    @GetMapping("/followers")
    fun getMyFollowers(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam pageNumber: Int
    ): ResponseEntity<List<BasicUserDataDTO>> =
        ResponseEntity.ok().body(enthusiastService.getMyFollowers(bearerToken, pageNumber))


    @GetMapping("/following")
    fun getUserIFollow(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam pageNumber: Int
    ): ResponseEntity<List<BasicUserDataDTO>> =
        ResponseEntity.ok().body(enthusiastService.getUserIFollow(bearerToken, pageNumber))


    @PostMapping("/followers/add")
    fun addFriend(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam username: String
    ): ResponseEntity<BasicUserDataDTO> =
        ResponseEntity.ok().body(enthusiastService.follow(bearerToken, username))

}