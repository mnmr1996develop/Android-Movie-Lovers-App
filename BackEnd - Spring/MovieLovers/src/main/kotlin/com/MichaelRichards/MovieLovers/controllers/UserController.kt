package com.MichaelRichards.MovieLovers.controllers

import com.MichaelRichards.MovieLovers.services.EnthusiastService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/users")
class UserController(
    private val enthusiastService: EnthusiastService,
) {
    @GetMapping("hello/")
    fun sayHello() = "hello"

}