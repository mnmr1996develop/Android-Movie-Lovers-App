package com.MichaelRichards.MovieLovers.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import java.util.*

@Entity
class Token(
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,


    var token: String = "",

    @Enumerated(EnumType.STRING)
    var tokenType: TokenType = TokenType.BEARER,

    var isNotExpired: Boolean = true,

    var isNotRevoked: Boolean = true,

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "enthusiast_id")
    val enthusiast: Enthusiast
){

    fun isValid(): Boolean = isNotExpired && isNotRevoked
    override fun toString(): String {
        return "Token(id=$id, token='$token', tokenType=$tokenType, isNotExpired=$isNotExpired, isNotRevoked=$isNotRevoked, enthusiast=$enthusiast)"
    }


}