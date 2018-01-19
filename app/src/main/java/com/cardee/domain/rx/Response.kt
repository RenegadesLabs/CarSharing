package com.cardee.domain.rx

data class Response<out T>(val body: T?, val errorCode: Int?, val errorMessage: String?) {
    val success: Boolean
        get() = errorCode == null
}
