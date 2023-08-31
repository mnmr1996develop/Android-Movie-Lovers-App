package com.MichaelRichards.MovieLovers.repositories

import com.MichaelRichards.MovieLovers.models.Enthusiast
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EnthusiastRepository : JpaRepository<Enthusiast, UUID> {
    fun findByUsername(username : String) : Enthusiast?
    fun findByEmail(email : String) : Enthusiast?
}