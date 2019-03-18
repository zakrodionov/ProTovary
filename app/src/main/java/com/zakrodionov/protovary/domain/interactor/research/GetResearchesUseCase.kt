package com.zakrodionov.protovary.domain.interactor.research

import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.domain.entity.ResearchesCategory
import com.zakrodionov.protovary.domain.interactor.UseCase
import com.zakrodionov.protovary.domain.repository.ResearchesRepository
import javax.inject.Inject

class GetResearchesUseCase
@Inject constructor(private val researchesRepository: ResearchesRepository) :
    UseCase<ResearchesCategory, GetResearchesUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, ResearchesCategory> =
        researchesRepository.getResearchesCategory(params.id)

    data class Params(val id: Long)
}