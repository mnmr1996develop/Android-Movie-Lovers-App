package com.MichaelRichards.MovieLovers.exceptions

import com.MichaelRichards.MovieLovers.dtos.APIException
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
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
    fun handleWebExchangeBindException(e: ConstraintViolationException): ResponseEntity<APIException> {
        val httpStatus = HttpStatus.BAD_REQUEST

        val firstViolation = e.constraintViolations.firstOrNull()


            val apiException =
                firstViolation?.let {
                    APIException(
                        httpStatus,
                        it.message,
                        "BAD_DATA",
                        LocalDateTime.now()
                    )
                }

        return ResponseEntity.status(httpStatus).body(apiException)
    }


    @ExceptionHandler(value = [CustomExceptions.InvalidTokenException::class])
    fun handleInvalidToken(exception: CustomExceptions.InvalidTokenException): ResponseEntity<Any> {
        val badRequest = HttpStatus.NOT_FOUND
        val apiException = exception.message?.let {
            APIException(
                badRequest,
                it,
                "BAD_TOKEN",
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

    @ExceptionHandler(value = [CustomExceptions.DuplicateReviewException::class])
    fun handleApiRequestException(exception: CustomExceptions.DuplicateReviewException): ResponseEntity<Any> {
        val badRequest = HttpStatus.BAD_REQUEST

        val apiException = exception.message?.let {
            APIException(
                badRequest,
                it,
                "DUPLICATE_REVIEW",
                LocalDateTime.now()
            )
        }
        return ResponseEntity(apiException, badRequest)
    }

    @ExceptionHandler(value = [CustomExceptions.InvalidReview::class])
    fun handleApiRequestException(exception: CustomExceptions.InvalidReview): ResponseEntity<APIException> {
        val code = HttpStatus.NOT_FOUND
        val apiException = exception.message?.let {
            APIException(
                code,
                it,
                "Invalid_REVIEW",
                LocalDateTime.now()
            )
        }
        return ResponseEntity.status(code).body(apiException)
    }

    @ExceptionHandler(value = [CustomExceptions.AlreadyFollowing::class])
    fun handleApiRequestException(exception: CustomExceptions.AlreadyFollowing): ResponseEntity<APIException> {
        val code = HttpStatus.BAD_REQUEST
        val apiException = exception.message?.let {
            APIException(
                code,
                it,
                "ALREADY_FOLLOWING",
                LocalDateTime.now()
            )
        }
        return ResponseEntity.status(code).body(apiException)
    }

    @ExceptionHandler(value = [CustomExceptions.CannotFindFollowing::class])
    fun handleApiRequestException(exception: CustomExceptions.CannotFindFollowing): ResponseEntity<APIException> {
        val code = HttpStatus.BAD_REQUEST
        val apiException = exception.message?.let {
            APIException(
                code,
                it,
                "DOES_NOT_FOLLOW",
                LocalDateTime.now()
            )
        }
        return ResponseEntity.status(code).body(apiException)
    }

    @ExceptionHandler(value = [CustomExceptions.InvalidUsername::class])
    fun handleApiRequestException(exception: CustomExceptions.InvalidUsername): ResponseEntity<Any> {
        val badRequest = HttpStatus.BAD_REQUEST
        val apiException = exception.message?.let {
            APIException(
                badRequest,
                it,
                "BAD_USERNAME",
                LocalDateTime.now()
            )
        }
        return ResponseEntity(apiException, badRequest)
    }

    @ExceptionHandler(value = [CustomExceptions.InvalidPassword::class])
    fun handleApiRequestException(exception: CustomExceptions.InvalidPassword): ResponseEntity<Any> {
        val badRequest = HttpStatus.BAD_REQUEST
        val apiException = exception.message?.let {
            APIException(
                badRequest,
                it,
                "BAD_PASSWORD",
                LocalDateTime.now()
            )
        }
        return ResponseEntity(apiException, badRequest)
    }

    @ExceptionHandler(value = [UsernameNotFoundException::class])
    fun handleApiRequestException(exception: UsernameNotFoundException): ResponseEntity<Any> {
        val badRequest = HttpStatus.NOT_FOUND
        val apiException = exception.message?.let {
            APIException(
                badRequest,
                it,
                "USERNAME_NOT_FOUND",
                LocalDateTime.now()
            )
        }
        return ResponseEntity(apiException, badRequest)
    }
}