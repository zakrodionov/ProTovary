package com.zakrodionov.protovary.app.platform

import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.functional.Either.Left
import com.zakrodionov.protovary.app.platform.Failure.*
import retrofit2.HttpException
import java.net.SocketTimeoutException
import javax.inject.Inject


class ErrorHandler @Inject constructor(private val networkHandler: NetworkHandler) {

    suspend fun <R> proceedException(
        exception: Throwable,
        updateFromDb: (suspend () -> R)? = null,
        specialBarcodeFailureHandler: (() -> String)? = null
    ): Either<Failure, R> {
        if (!networkHandler.isConnected) {
            val data = updateFromDb?.invoke()
            return if (data == null) Left(NetworkConnection) else Left(CacheFailure(data))

        }
        when {
            exception is HttpException -> {
                return Left(ServerError)
            }
            exception is SocketTimeoutException -> {
                return Left(ServerError)
            }
            specialBarcodeFailureHandler != null -> {
                return Left(BarcodeFailure(specialBarcodeFailureHandler()))
            }
            else -> Left(UnknownError)
        }
        return Left(UnknownError)
    }
}