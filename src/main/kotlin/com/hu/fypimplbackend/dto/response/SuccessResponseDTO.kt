package com.hu.fypimplbackend.dto.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class SuccessResponseDTO(
    var payload: Any,

    status: HttpStatus,
    hasError: Boolean = false
) : BaseResponse(hasError, status) {
    override fun toString(): String {
        return "SuccessDTO(result=$payload)"
    }

    companion object {
        fun getSuccessObject(payload: Any, httpStatus: HttpStatus): ResponseEntity<BaseResponse> = ResponseEntity.ok().body(SuccessResponseDTO(payload, httpStatus))
        fun getDeleteResponse(): ResponseEntity<Any> = ResponseEntity.noContent().build()
    }
}