package com.zakrodionov.protovary.app.platform

/**
 * Base Class for handling errors/failures/exceptions.
 */
sealed class Failure {
    object NetworkConnection : Failure()
    object ServerError : Failure()
    object UnknownError : Failure()
    object CancellationError : Failure()
    object NullDataError : Failure()

    class BarcodeFailure(val barcode: String) : Failure()
}
