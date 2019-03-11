package com.zakrodionov.roskachestvo.app.platform

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.functional.Either.Left
import com.zakrodionov.roskachestvo.app.platform.Failure.*
import retrofit2.HttpException
import javax.inject.Inject


class ErrorHandler @Inject constructor(private val networkHandler: NetworkHandler) {

    suspend fun <R> proceedException(
        exception: Throwable,
        updateFromDb: (suspend () -> R)? = null
    ): Either<Failure, R> {
        if (!networkHandler.isConnected) {
            val data = updateFromDb?.invoke()
            return if (data == null) Left(NetworkConnection) else Left(CacheFailure(data))

        }
        when (exception) {
            is HttpException -> {
                return Left(ServerError)
            }
        }
        return Left(UnknownError)
    }
}