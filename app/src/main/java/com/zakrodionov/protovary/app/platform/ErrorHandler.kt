package com.zakrodionov.protovary.app.platform

import com.zakrodionov.protovary.app.platform.Failure.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.util.concurrent.CancellationException

class ErrorHandler(private val networkHandler: NetworkHandler) {

    fun proceedException(
        exception: Throwable,
        specialBarcodeErrorHandler: String? = ""
    ): Failure {
        when {
            !networkHandler.isConnected -> {
                return NetworkConnection
            }
            specialBarcodeErrorHandler != null -> {
                return BarcodeFailure(specialBarcodeErrorHandler.orEmpty())
            }
            exception is HttpException -> {
                return ServerError
            }
            exception is SocketTimeoutException -> {
                return ServerError
            }
            exception is CancellationException -> {
                return CancellationError
            }
            exception is KotlinNullPointerException -> {
                return NullDataError
            }
            else -> UnknownError
        }
        return UnknownError
    }
}
