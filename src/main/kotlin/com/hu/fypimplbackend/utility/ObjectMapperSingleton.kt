package com.hu.fypimplbackend.utility

import com.fasterxml.jackson.databind.ObjectMapper

object ObjectMapperSingleton {
    val objectMapper: ObjectMapper = ObjectMapper()
}