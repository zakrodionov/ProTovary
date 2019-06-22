package com.zakrodionov.protovary.domain.interactor

import com.zakrodionov.protovary.app.platform.ErrorHandler
import com.zakrodionov.protovary.app.platform.State

/**
 * Created by Zakhar Rodionov on 23.05.19.
 */
abstract class BaseInteractor(val errorHandler: ErrorHandler) {

    /*Оборачиваем функцию в try-catch и подвязываем state*/
    protected suspend fun execute(
        onState: (State) -> Unit,
        func: suspend () -> Unit
    ) {
        try {
            onState.invoke(State.Loading)
            func.invoke()
            onState.invoke(State.Loaded)
        } catch (e: Exception) {
            onState.invoke(State.Error(errorHandler.proceedException(e)))
        }
    }

}