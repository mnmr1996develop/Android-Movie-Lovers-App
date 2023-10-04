package com.MichaelRichards.MovieLovers.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.data.jpa.domain.AbstractPersistable_.id
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*


@Entity
class Enthusiast(
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: UUID? = null,

    @Column(unique = true)
    @field:Size(min = 5, message = "Username must be at least 5 characters")
    @field:Size(max = 20, message = "Maximum Username length is 20 characters")
    private var username: String = "",

    @field:Size(min = 2, message = "First name must be more than 1 character")
    @field:Size(max = 30, message = "Max first name is 30 characters long")
    var firstName: String = "",

    @field:Size(min = 2, message = "Last name must be more than 1 character")
    @field:Size(max = 30, message = "Max last name is 30 characters long")
    var lastName: String = "",

    @field:Email(message = "Email Not Valid")
    @field:Size(min = 5, message = "Email Not Valid")
    var email: String = "",

    var birthday: LocalDate? = null,


    private var password: String = "",

    @Enumerated(value = EnumType.STRING)
    var role: Role = Role.ROLE_USER,


    var createdAt: LocalDateTime,

    @JsonIgnore
    var updatedAt: LocalDateTime,

): UserDetails {
    

    @JsonIgnore
    @OneToMany(mappedBy = "enthusiast", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val _movieReviews : MutableList<MovieReview> = mutableListOf()

    @Column
    @JsonIgnore
    @OneToMany(mappedBy = "follower")
    val followers: MutableList<Followers> = mutableListOf()


    @Column
    @JsonIgnore
    @OneToMany(mappedBy = "followee")
    val following: MutableList<Followers> = mutableListOf()

    @get:JsonIgnore
    val movieReviews get() = _movieReviews.toList()

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableSetOf(
        SimpleGrantedAuthority(role.name)
    )


    @JsonIgnore
    override fun getPassword(): String = this.password


    override fun getUsername(): String = this.username


    @JsonIgnore
    override fun isAccountNonExpired(): Boolean = true

    @JsonIgnore
    override fun isAccountNonLocked(): Boolean = true

    @JsonIgnore
    override fun isCredentialsNonExpired(): Boolean = true

    @JsonIgnore
    override fun isEnabled(): Boolean = true

    fun addReview(movieReview: MovieReview){
        _movieReviews.add(movieReview)
    }

    fun deleteReview(movieReview: MovieReview) {
        _movieReviews.remove(movieReview)
    }


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Enthusiast

        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }


}