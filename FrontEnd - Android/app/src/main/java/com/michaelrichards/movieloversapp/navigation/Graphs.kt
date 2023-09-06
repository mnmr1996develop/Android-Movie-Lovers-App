package com.michaelrichards.movieloversapp.navigation

sealed class Graphs(val routeName: String){
    object StartGraph : Graphs("start")
    object AuthGraph: Graphs("auth")
    object MainGraph: Graphs("main")
}