package com.apolo.tracking.domain.model

class ApiException(val code: Int, message: String) : Exception(message)
