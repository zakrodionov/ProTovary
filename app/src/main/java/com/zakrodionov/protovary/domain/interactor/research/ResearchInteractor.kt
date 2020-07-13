package com.zakrodionov.protovary.domain.interactor.research

import com.zakrodionov.protovary.app.platform.ErrorHandler
import com.zakrodionov.protovary.app.platform.State
import com.zakrodionov.protovary.data.entity.Research
import com.zakrodionov.protovary.data.entity.Researches
import com.zakrodionov.protovary.data.repository.ResearchRepository
import com.zakrodionov.protovary.domain.interactor.BaseInteractor

class ResearchInteractor(
    private val researchRepository: ResearchRepository,
    errorHandler: ErrorHandler
) : BaseInteractor(errorHandler) {

    suspend fun getResearch(
        id: Long,
        onSuccess: (Research) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onSuccess, onState) {
            researchRepository.getResearch(id)
        }
    }

    suspend fun getResearches(
        onSuccess: (List<Researches>) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onSuccess, onState) {
            researchRepository.getResearches()
        }
    }
}
