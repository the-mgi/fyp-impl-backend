package com.hu.fypimplbackend.exceptions

class InvalidOTPCodeException(message: String = "Invalid OTP code received") : Exception(message)
class NestedObjectDoesNotExistException(message: String) : Exception(message)
class ResourceAccessForbidden(message: String = "Forbidden") : Exception(message)