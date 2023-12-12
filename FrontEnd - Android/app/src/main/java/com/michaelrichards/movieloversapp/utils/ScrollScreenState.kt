package com.michaelrichards.movieloversapp.utils

data class ScrollScreenState<T>(
    val isLoading: Boolean = false,
    var items: List<T> = listOf(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 0
)
