package com.michaelrichards.movieloversapp.repositories.auth

sealed class AuthResult<T>(val data: T? = null) {
    class Authorized<T>(data: T? = null): AuthResult<T>(data)
    class UnAuthorized<T>(data: T? = null): AuthResult<T>(data)
    class UnknownError<T>(data: T? = null): AuthResult<T>(data)

}
