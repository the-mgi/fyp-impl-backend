package com.hu.fypimplbackend.exceptions

import com.hu.fypimplbackend.dto.response.ErrorResponseDTO
import com.hu.fypimplbackend.dto.response.ErrorResponseDTO.Companion.getErrorObject
import org.slf4j.Logger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.persistence.EntityNotFoundException

@ControllerAdvice
class RestResponseEntityExceptionHandler(
    @Autowired
    private val loggerFactory: Logger

) : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleEntityNotFoundException(
        entityNotFoundException: EntityNotFoundException,
        webRequest: WebRequest
    ): ResponseEntity<ErrorResponseDTO> {
        loggerFactory.warn("EntityNotFoundException occurred")
        return getErrorObject("Resource not found", entityNotFoundException.message!!, HttpStatus.NOT_FOUND.value())
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleDataIntegrityViolationException(
        dataIntegrityViolationException: DataIntegrityViolationException,
        webRequest: WebRequest
    ): ResponseEntity<ErrorResponseDTO> {
        loggerFactory.warn("DataIntegrityViolationException occurred")
        return getErrorObject("Conflict", dataIntegrityViolationException.message!!, HttpStatus.CONFLICT.value())
    }

    @ExceptionHandler(NestedObjectDoesNotExistException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNestedObjectDoesNotExistException(
        nestedObjectDoesNotExistException: NestedObjectDoesNotExistException,
        webRequest: WebRequest
    ): ResponseEntity<ErrorResponseDTO> {
        loggerFactory.warn("NestedObjectDoesNotExistException occurred")
        return getErrorObject("Not found", nestedObjectDoesNotExistException.message!!, HttpStatus.NOT_FOUND.value())
    }

    @ExceptionHandler(RuntimeException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleRuntimeException(
        runtimeException: RuntimeException,
        webRequest: WebRequest
    ): ResponseEntity<ErrorResponseDTO> {
        loggerFactory.warn("RuntimeException occurred")
        return getErrorObject("Runtime Exception", runtimeException.message!!, HttpStatus.INTERNAL_SERVER_ERROR.value())
    }

    @ExceptionHandler(InvalidOTPCodeException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleInvalidOTPCodeException(
        invalidOTPCodeException: InvalidOTPCodeException,
        webRequest: WebRequest
    ) : ResponseEntity<ErrorResponseDTO> {
        loggerFactory.warn("InvalidOTPCodeException occurred")
        return getErrorObject("OTP Code Invalid", invalidOTPCodeException.message!!, HttpStatus.NOT_FOUND.value())
    }
}