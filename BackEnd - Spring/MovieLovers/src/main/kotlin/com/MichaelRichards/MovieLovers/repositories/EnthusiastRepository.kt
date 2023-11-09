package com.MichaelRichards.MovieLovers.repositories

import com.MichaelRichards.MovieLovers.models.Enthusiast
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*
import kotlin.collections.List

@Repository
interface EnthusiastRepository : JpaRepository<Enthusiast, UUID> {
    fun findByUsername(username: String): Enthusiast?
    fun findByEmail(email: String): Enthusiast?

    @Query(
        "SELECT e FROM Enthusiast e WHERE " +
                "e.username LIKE CONCAT('%',:query, '%')" +
                "Or e.firstName LIKE CONCAT('%', :query, '%')" +
                "Or e.lastName LIKE CONCAT('%', :query, '%')" +
                "Or e.email LIKE CONCAT('%', :query, '%')"
    )
    fun searchUsers(query: String): List<Enthusiast>
}