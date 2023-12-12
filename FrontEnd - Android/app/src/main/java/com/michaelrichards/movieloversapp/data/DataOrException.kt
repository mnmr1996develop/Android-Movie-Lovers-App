package com.michaelrichards.movieloversapp.data

import kotlin.reflect.KProperty


data class DataOrException<T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null
) {

}