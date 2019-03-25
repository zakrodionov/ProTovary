package com.zakrodionov.protovary.app.platform


/**
 * Base Class for handling errors/failures/exceptions.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object UnknownError : Failure()

    class CacheFailure<T>(val data: T) : Failure()
    class BarcodeFailure(val barcode: String) : Failure()

}