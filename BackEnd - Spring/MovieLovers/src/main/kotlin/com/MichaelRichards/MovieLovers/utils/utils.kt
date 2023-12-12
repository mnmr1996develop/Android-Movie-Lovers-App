package com.MichaelRichards.MovieLovers.utils

fun isPageNumberValid(pageNumber: Int){
    if (pageNumber < 0) {
        throw IndexOutOfBoundsException("$pageNumber is not valid buddy")
    }
}