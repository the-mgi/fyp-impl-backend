package com.hu.fypimplbackend.dto.response

import org.springframework.http.ResponseEntity

class SuccessResponseDTO(
    var payload: Any,

    status: Int,
    hasError: Boolean = false
) : BaseResponse(hasError, status) {
    override fun toString(): String {
        return "SuccessDTO(result=$payload)"
    }

    companion object {
        fun getSuccessObject(payload: Any, httpStatus: Int): ResponseEntity<BaseResponse> =
            ResponseEntity.status(httpStatus).body(SuccessResponseDTO(payload, httpStatus))

        fun getDeleteResponse(): ResponseEntity<Any> = ResponseEntity.noContent().build()
    }
}