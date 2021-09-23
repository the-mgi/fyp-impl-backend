package com.hu.fypimplbackend.exceptions

import com.hu.fypimplbackend.dto.response.ErrorResponseDTO
import com.hu.fypimplbackend.dto.response.ErrorResponseDTO.Companion.getErrorObject
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
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(EntityNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun entityNotFoundException(
        entityNotFoundException: EntityNotFoundException,
        webRequest: WebRequest
    ): ResponseEntity<ErrorResponseDTO> {
        return getErrorObject("Resource not found", entityNotFoundException.message!!, HttpStatus.NOT_FOUND.value())
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun dataIntegrityViolationException(
        dataIntegrityViolationException: DataIntegrityViolationException,
        webResponseStatus: ResponseStatus
    ) : ResponseEntity<ErrorResponseDTO> {
        return getErrorObject("Conflict", dataIntegrityViolationException.message!!, HttpStatus.CONFLICT.value())
    }
}