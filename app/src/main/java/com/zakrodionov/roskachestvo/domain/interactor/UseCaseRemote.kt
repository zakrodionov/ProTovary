package com.zakrodionov.roskachestvo.domain.interactor

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 *
 * By convention each [UseCaseRemote] implementation will execute its job in a background thread
 * (kotlin coroutine) and will post the result in the UI thread.
 */
abstract class UseCaseRemote<out Type, in Params> where Type : Any {

    private var parentJob: Job = Job()
    var backgroundContext: CoroutineContext = Dispatchers.IO
    var foregroundContext: CoroutineContext = Dispatchers.Main

    abstract suspend fun run(params: Params): Either<Failure, Type>

    operator fun invoke(params: Params, onResult: (Either<Failure, Type>) -> Unit = {}) {

        unsubscribe()
        parentJob = Job()

        CoroutineScope(foregroundContext + parentJob).launch {
            val result = withContext(backgroundContext) {
                run(params)
            }

            onResult(result)

        }

    }

    fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

    class None
}