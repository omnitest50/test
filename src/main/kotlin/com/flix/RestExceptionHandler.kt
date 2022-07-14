package com.flix

import com.flix.reservation.BusinessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler
    fun handleBusinessException(ex: BusinessException): ResponseEntity<ErrorMessageDTO> =
        ResponseEntity(ErrorMessageDTO(ex.message), HttpStatus.BAD_REQUEST)

}

class ErrorMessageDTO(
    var message: String? = null
)