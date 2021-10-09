package com.hu.fypimplbackend.utility.mappers

import org.mapstruct.Condition

object PresenceCheckUtils {
    @Condition
    fun isNotEmpty(value: Any?): Boolean {
        return value != null && when (value) {
            is String -> value.length > 0
            else -> true
        }
    }
}