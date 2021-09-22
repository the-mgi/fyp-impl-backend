package com.hu.fypimplbackend.dto.response

import org.springframework.http.HttpStatus

class ErrorDTO(
    var message: String? = null,
    var error: String? = null,

    hasError: Boolean = true,
    status: HttpStatus
) : BaseResponse(hasError, status) {
    override fun toString(): String {
        return "ErrorDTO(message=$message, error=$error)"
    }
}