package com.michaelrichards.movieloversapp.dtos

data class ErrorResponse(
    val status: String,
    val message: String,
    val reason: String,
    val timeStamp: String
) {
    override fun toString(): String {
        return "ErrorResponse(status='$status', message='$message', reason='$reason', timeStamp='$timeStamp')"
    }
}
