package com.zakrodionov.roskachestvo.model

sealed class RequestResult<out T> {
    data class Success<out T>(val data: T) : RequestResult<T>()
    data class Error<out T>(val throwable: Throwable) : RequestResult<T>()
}