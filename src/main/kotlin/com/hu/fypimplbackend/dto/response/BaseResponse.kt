package com.hu.fypimplbackend.dto.response

open class BaseResponse(
    var hasError: Boolean,
    var status: Int
) : Any() {
    override fun toString(): String {
        return "BaseResponse(hasError=$hasError, status=$status)"
    }
}