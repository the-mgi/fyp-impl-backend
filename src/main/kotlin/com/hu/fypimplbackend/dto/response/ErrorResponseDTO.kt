package com.hu.fypimplbackend.dto.response

import org.springframework.http.ResponseEntity

class ErrorResponseDTO(
    var message: String? = null,
    var error: String? = null,

    status: Int,
    hasError: Boolean = true
) : BaseResponse(hasError, status) {
    override fun toString(): String {
        return "ErrorDTO(message=$message, error=$error)"
    }

    companion object {
        fun getErrorObject(message: String, error: String, httpStatus: Int): ResponseEntity<ErrorResponseDTO> = ResponseEntity.status(httpStatus).body(ErrorResponseDTO(message, error, httpStatus))
    }
}