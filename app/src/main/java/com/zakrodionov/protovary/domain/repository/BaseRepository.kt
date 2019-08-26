package com.zakrodionov.protovary.domain.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseRepository {

    /*Получаем данные в IO потоке и возвращаем в Main*/
    suspend fun <L> request(
        call: L
    ) = withContext(Dispatchers.IO) {
        val result = call

        withContext(Dispatchers.Main) {
            result
        }
    }
}