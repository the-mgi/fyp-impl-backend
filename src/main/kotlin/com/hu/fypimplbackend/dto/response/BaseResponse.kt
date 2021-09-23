package com.hu.fypimplbackend.dto.response

open class BaseResponse(
    var hasError: Boolean,
    var status: Int
) {
    override fun toString(): String {
        return "BaseResponse(hasError=$hasError, status=$status)"
    }
}