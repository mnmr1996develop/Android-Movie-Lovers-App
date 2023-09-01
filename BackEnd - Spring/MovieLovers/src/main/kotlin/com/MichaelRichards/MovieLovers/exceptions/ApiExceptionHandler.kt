package com.MichaelRichards.MovieLovers.exceptions

import com.MichaelRichards.MovieLovers.dtos.APIException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(value = [CustomExceptions.UsernameTakenException::class])
    fun handleApiRequestException(exception: CustomExceptions.UsernameTakenException): ResponseEntity<Any> {
        val badRequest = HttpStatus.BAD_REQUEST
        val apiException = exception.message?.let {
            APIException(
                badRequest,
                it,
                "USERNAME_TAKEN",
                LocalDateTime.now()
            )
        }
        return ResponseEntity.status(badRequest).body(apiException)
    }

    @ExceptionHandler(value = [BadCredentialsException::class])
    fun handleApiRequestException(exception: BadCredentialsException): ResponseEntity<Any> {
        val badRequest = HttpStatus.BAD_REQUEST
        val apiException = exception.message?.let {
            APIException(
                badRequest,
                it,
                "BAD_LOGIN_DATA",
                LocalDateTime.now()
            )
        }
        return ResponseEntity(apiException, badRequest)
    }


    @ExceptionHandler(ConstraintViolationException::class)
    fun handleWebExchangeBindException(e: ConstraintViolationException): ResponseEntity<MutableList<APIException>> {
        val badRequest = HttpStatus.BAD_REQUEST
        val violations = mutableListOf<APIException>()
        for (violation in e.constraintViolations){
            val apiException =
                APIException(
                    badRequest,
                    violation.message,
                    "BAD_DATA",
                    LocalDateTime.now()
                )

            violations.add(apiException)
        }

        return ResponseEntity(violations, badRequest)
    }


    @ExceptionHandler(value = [CustomExceptions.InvalidTokenException::class])
    fun handleInvalidToken(exception: CustomExceptions.InvalidTokenException): ResponseEntity<Any> {
        val badRequest = HttpStatus.NOT_FOUND
        val apiException = exception.message?.let {
            APIException(
                badRequest,
                it,
                "USERNAME_TAKEN",
                LocalDateTime.now()
            )
        }
        return ResponseEntity.status(badRequest).body(apiException)
    }

    @ExceptionHandler(value = [CustomExceptions.EmailTakenException::class])
    fun handleApiRequestException(exception: CustomExceptions.EmailTakenException): ResponseEntity<Any> {
        val badRequest = HttpStatus.BAD_REQUEST
        val apiException = exception.message?.let {
            APIException(
                badRequest,
                it,
                "EMAIL_TAKEN",
                LocalDateTime.now()
            )
        }
        return ResponseEntity(apiException, badRequest)
    }

    @ExceptionHandler(value = [CustomExceptions.InvalidAge::class])
    fun handleApiRequestException(exception: CustomExceptions.InvalidAge): ResponseEntity<Any> {
        val badRequest = HttpStatus.BAD_REQUEST

        val apiException = exception.message?.let {
            APIException(
                badRequest,
                it,
                if (exception.age in 0..12) "TOO_YOUNG" else "INVALID_AGE",
                LocalDateTime.now()
            )
        }
        return ResponseEntity(apiException, badRequest)
    }
}