package com.hu.fypimplbackend.dto.response

import org.springframework.http.HttpStatus

open class BaseResponse(
    var hasError: Boolean,
    var status: HttpStatus
) {
    override fun toString(): String {
        return "BaseResponse(hasError=$hasError, status=$status)"
    }
}