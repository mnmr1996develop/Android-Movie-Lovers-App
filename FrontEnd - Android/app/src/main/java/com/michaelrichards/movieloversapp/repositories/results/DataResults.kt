package com.michaelrichards.movieloversapp.repositories.results

sealed class DataResults <T>(val data: T? = null) {
    class Ok<T>(data: T? = null, ): DataResults<T>(data)
    class UnAuthorized<T>(data: T? = null):DataResults<T>(data)
    class UnknownError<T>(data: T? = null): DataResults<T>(data)
    class BadRequest<T>(data: T? = null): DataResults<T>(data)
}