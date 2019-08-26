package com.zakrodionov.protovary.domain.interactor.research

import com.zakrodionov.protovary.app.platform.ErrorHandler
import com.zakrodionov.protovary.app.platform.State
import com.zakrodionov.protovary.data.db.ResearchDao
import com.zakrodionov.protovary.data.entity.Research
import com.zakrodionov.protovary.data.entity.Researches
import com.zakrodionov.protovary.data.repository.ResearchRepository
import com.zakrodionov.protovary.domain.interactor.BaseInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ResearchInteractor(
    val researchRepository: ResearchRepository,
    val researchDao: ResearchDao,
    errorHandler: ErrorHandler
) : BaseInteractor(errorHandler) {

    suspend fun getResearch(
        id: Long,
        onSuccess: (Research) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onState) {
            val result = researchRepository.getResearch(id)
            onSuccess.invoke(result)
        }
    }

    suspend fun getResearchesCategory(
        id: Long,
        onSuccess: (Researches) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onState) {
            val result = researchRepository.getResearchesCategory(id)
            onSuccess.invoke(result)
        }
    }

    suspend fun getResearches(
        onSuccess: (List<Researches>) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onState) {
            val result = researchRepository.getResearches()

            withContext(Dispatchers.IO) {
                researchDao.insertResearchesCategory(result)
            }

            onSuccess.invoke(result)
        }
    }
}