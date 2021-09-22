package com.hu.fypimplbackend.dto.response

import org.springframework.http.HttpStatus

class SuccessDTO(
    var payload: Any,

    hasError: Boolean,
    status: HttpStatus
) : BaseResponse(hasError, status) {
    override fun toString(): String {
        return "SuccessDTO(result=$payload)"
    }
}