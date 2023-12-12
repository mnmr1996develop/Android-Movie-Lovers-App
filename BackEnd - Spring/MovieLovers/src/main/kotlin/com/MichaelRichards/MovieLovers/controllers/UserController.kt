package com.MichaelRichards.MovieLovers.controllers

import com.MichaelRichards.MovieLovers.dtos.BasicSearchUserDataDTO
import com.MichaelRichards.MovieLovers.dtos.BasicUserDataDTO
import com.MichaelRichards.MovieLovers.dtos.ProfileDataDTO
import com.MichaelRichards.MovieLovers.services.EnthusiastService
import com.MichaelRichards.MovieLovers.services.FollowService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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
    private val followService: FollowService
) {

    @GetMapping
    fun getUserDetails(@RequestHeader("Authorization") bearerToken: String): ResponseEntity<ProfileDataDTO> {
        val enthusiast = enthusiastService.getBasicUserDataByBearerToken(bearerToken)
        return ResponseEntity.ok().body(enthusiast)
    }

    @GetMapping("user/{username}")
    fun findByUser(@RequestHeader("Authorization") bearerToken: String, @PathVariable username: String): ResponseEntity<ProfileDataDTO> {
        return ResponseEntity.ok().body(enthusiastService.getByUsername(username, bearerToken))
    }

    @GetMapping("/search")
    fun searchUser(@RequestHeader("Authorization") bearerToken: String, @RequestParam query: String): ResponseEntity<List<ProfileDataDTO>> =
        ResponseEntity.ok().body(enthusiastService.searchUser(bearerToken, query))

    @GetMapping("/followers/full")
    fun getMyFollower(
        @RequestHeader("Authorization") bearerToken: String
    ): ResponseEntity<List<BasicUserDataDTO>> =
        ResponseEntity.ok().body(followService.getMyFollowers(bearerToken))

    @GetMapping("/followers")
    fun getMyFollowersPaged(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam pageNumber: Int
    ): ResponseEntity<List<ProfileDataDTO>> =
        ResponseEntity.ok().body(followService.getMyFollowersPaged(bearerToken, pageNumber))


    @GetMapping("/following/full")
    fun getUserIFollowPaged(
        @RequestHeader("Authorization") bearerToken: String,
    ): ResponseEntity<List<ProfileDataDTO>> =
        ResponseEntity.ok().body(followService.getUserIFollow(bearerToken) )

    @GetMapping("/following")
    fun getUserIFollow(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam pageNumber: Int
    ): ResponseEntity<List<ProfileDataDTO>> =
        ResponseEntity.ok().body(followService.getUserIFollowPaged(bearerToken, pageNumber))


    @PostMapping("/follow")
    fun follow(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam username: String
    ): ResponseEntity<BasicUserDataDTO> =
        ResponseEntity.ok().body(followService.follow(bearerToken, username))

    @DeleteMapping("/follow")
    fun unfollow(
        @RequestHeader("Authorization") bearerToken: String,
        @RequestParam username: String
    ): ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(followService.unfollow(bearerToken, username))

}