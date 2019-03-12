package com.zakrodionov.roskachestvo.domain.interactor.research

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory
import com.zakrodionov.roskachestvo.domain.interactor.UseCaseRemote
import com.zakrodionov.roskachestvo.domain.repository.ResearchesRepository
import javax.inject.Inject

class GetResearchesCategoryUseCase
@Inject constructor(private val researchesRepository: ResearchesRepository) :
    UseCaseRemote<ResearchesCategory, GetResearchesCategoryUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, ResearchesCategory> =
        researchesRepository.getResearchesCategory(params.id)

    data class Params(val id: Long)
}