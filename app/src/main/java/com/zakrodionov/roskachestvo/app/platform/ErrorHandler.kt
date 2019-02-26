package com.zakrodionov.roskachestvo.app.platform

import com.zakrodionov.roskachestvo.app.functional.Either
import javax.inject.Inject


class ErrorHandler @Inject constructor(private val networkHandler: NetworkHandler) {

    suspend fun <R> proceedException(
        exception: Throwable,
        updateFromDb: (suspend () -> R)? = null
    ): Either<Failure, R> {
        if (!networkHandler.isConnected) {
            val data = updateFromDb?.invoke()
            return if (data == null) Either.Left(Failure.NetworkConnection) else Either.Left(Failure.CacheFailure(data))

        }
        when (exception) {
            //реализовать
        }
        return Either.Left(Failure.ServerError)
    }
}