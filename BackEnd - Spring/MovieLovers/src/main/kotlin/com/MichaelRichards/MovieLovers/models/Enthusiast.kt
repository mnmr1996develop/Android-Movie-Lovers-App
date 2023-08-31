package com.MichaelRichards.MovieLovers.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
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
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(unique = true)
    @field:NotBlank(message = "Username must not be blank")
    private var username: String = "",

    @field:NotBlank(message = "First Name must not be blank")
    @field:Size(min = 2, message = "First name must be more than 1 character")
    @field:Size(max = 30, message = "Max first name is 30 characters long")
    var firstName: String = "",

    @field:Size(min = 2, message = "Last name must be more than 1 character")
    @field:Size(max = 30, message = "Max last name is 30 characters long")
    @field:NotBlank(message = "Last Name must not be blank")
    var lastName: String = "",

    @field:Email
    var email: String = "",

    var birthday: LocalDate? = null,


    private var password: String = "",

    @Enumerated(value = EnumType.STRING)
    var role: Role = Role.ROLE_USER,


    var createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null
): UserDetails {

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
}