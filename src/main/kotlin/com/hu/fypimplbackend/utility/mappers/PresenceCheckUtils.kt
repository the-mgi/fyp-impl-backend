package com.hu.fypimplbackend.utility.mappers

import org.mapstruct.Condition

object PresenceCheckUtils {
    @Condition
    fun isNotEmpty(value: String?): Boolean {
        return value != null && value.isNotEmpty()
    }
}