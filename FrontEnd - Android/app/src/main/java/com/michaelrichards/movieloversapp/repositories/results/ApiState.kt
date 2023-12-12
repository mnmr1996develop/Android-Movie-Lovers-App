package com.michaelrichards.movieloversapp.repositories.results

sealed class ApiState <T>(val data: T? = null) {
    class Ok<T>(data: T? = null, ): ApiState<T>(data)
    class UnAuthorized<T>(data: T? = null):ApiState<T>(data)
    class UnknownError<T>(data: T? = null): ApiState<T>(data)
    class BadRequest<T>(data: T? = null): ApiState<T>(data)

    class Loading<T>: ApiState<T>()
}