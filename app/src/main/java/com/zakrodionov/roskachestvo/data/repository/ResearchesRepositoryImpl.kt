package com.zakrodionov.roskachestvo.data.repository

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.ErrorHandler
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.data.db.ResearchDao
import com.zakrodionov.roskachestvo.data.network.Api
import com.zakrodionov.roskachestvo.domain.entity.Research
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory
import com.zakrodionov.roskachestvo.domain.repository.ResearchesRepository
import javax.inject.Inject

class ResearchesRepositoryImpl @Inject constructor(
    private val api: Api,
    private val researchDao: ResearchDao,
    private val errorHandler: ErrorHandler
) : ResearchesRepository {

    override suspend fun getResearches(): Either<Failure, List<ResearchesCategory>> {
        return try {
            val result = api.getResearches().await()
            val data = result
            researchDao.insertResearchesCategory(data)
            Either.Right(data)
        } catch (exception: Throwable) {
            errorHandler.proceedException(exception)
        }
    }

    override suspend fun getResearch(id: Long): Either<Failure, Research> {
        return try {
            val result = api.getResearch(id).await()
            val data = result
            Either.Right(data)
        } catch (exception: Throwable) {
            errorHandler.proceedException(exception)
        }
    }

    override suspend fun getResearchesCategory(id: Long): Either<Failure, ResearchesCategory> {
        return try {
            val data = researchDao.getResearchCategoryById(id)
            Either.Right(data)
        } catch (exception: Throwable) {
            errorHandler.proceedException(exception)
        }
    }
}