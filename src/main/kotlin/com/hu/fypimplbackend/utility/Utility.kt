package com.hu.fypimplbackend.utility

const val USER_ROUTE = "/user"
const val INTEREST_ROUTE = "/interest"

val OPEN_ROUTES = arrayOf(
    "$USER_ROUTE/login",
    "$USER_ROUTE/save",
    "$USER_ROUTE/forgot-password",
    "$USER_ROUTE/update-password",
)

const val DIGIT_START = 48
const val UPPERCASE_START = 65
const val LOWER_CASE_START = 97
const val PASSWORD_LENGTH = 10

fun generateString(): String {
    fun generateDigit(): Char {
        return (DIGIT_START + Math.random() * 10).toInt().toChar()
    }

    fun generateUpperCase(): Char {
        return (UPPERCASE_START + Math.random() * 26).toInt().toChar()
    }

    fun generateLowerCase(): Char {
        return (LOWER_CASE_START + Math.random() * 26).toInt().toChar()
    }

    var length = 1
    var a = 1
    var str = ""
    while (length <= PASSWORD_LENGTH) {
        when (a) {
            1 -> {
                str += generateDigit()
            }
            2 -> {
                str += generateUpperCase()
            }
            3 -> {
                str += generateLowerCase()
            }
            else -> {
                a = 0
            }
        }
        a += 1
        length += 1
    }
    return str
}