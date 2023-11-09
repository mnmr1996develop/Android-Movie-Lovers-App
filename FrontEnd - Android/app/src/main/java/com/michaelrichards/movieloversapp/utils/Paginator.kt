package com.michaelrichards.movieloversapp.utils

interface Paginator< Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}