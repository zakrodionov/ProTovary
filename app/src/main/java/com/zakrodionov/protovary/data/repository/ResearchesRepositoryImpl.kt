package com.zakrodionov.protovary.data.repository

import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.platform.ErrorHandler
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.data.db.ResearchDao
import com.zakrodionov.protovary.data.network.Api
import com.zakrodionov.protovary.domain.entity.Research
import com.zakrodionov.protovary.domain.entity.Researches
import com.zakrodionov.protovary.domain.repository.ResearchesRepository
import javax.inject.Inject

class ResearchesRepositoryImpl @Inject constructor(
    private val api: Api,
    private val researchDao: ResearchDao,
    private val errorHandler: ErrorHandler
) : ResearchesRepository {

    override suspend fun getResearches(): Either<Failure, List<Researches>> {
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

    override suspend fun getResearchesCategory(id: Long): Either<Failure, Researches> {
        return try {
            val data = researchDao.getResearchCategoryById(id)
            Either.Right(data)
        } catch (exception: Throwable) {
            errorHandler.proceedException(exception)
        }
    }
}