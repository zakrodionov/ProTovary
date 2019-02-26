package com.zakrodionov.roskachestvo.data.repository

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.ErrorHandler
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.data.db.ProductDao
import com.zakrodionov.roskachestvo.data.network.Api
import com.zakrodionov.roskachestvo.domain.entity.Researches
import com.zakrodionov.roskachestvo.domain.repository.ResearchesRepository
import javax.inject.Inject

class ResearchesRepositoryImpl @Inject constructor(
    private val api: Api,
    private val productDao: ProductDao,
    private val errorHandler: ErrorHandler
) : ResearchesRepository {

    override suspend fun getResearches(): Either<Failure, List<Researches>> {
        return try {
            val result = api.getResearches().await()
            val data = result //тут мап
            //тут вставляем в дб
            Either.Right(data)
        } catch (exception: Throwable) {
            errorHandler.proceedException(exception) //тут функция апдеета из дб
        }
    }
}