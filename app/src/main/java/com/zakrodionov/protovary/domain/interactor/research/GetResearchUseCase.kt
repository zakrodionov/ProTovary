package com.zakrodionov.protovary.domain.interactor.research

import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.domain.entity.Research
import com.zakrodionov.protovary.domain.interactor.UseCase
import com.zakrodionov.protovary.domain.interactor.research.GetResearchUseCase.Params
import com.zakrodionov.protovary.domain.repository.ResearchesRepository
import javax.inject.Inject

class GetResearchUseCase
@Inject constructor(private val researchesRepository: ResearchesRepository) : UseCase<Research, Params>() {

    override suspend fun run(params: Params): Either<Failure, Research> = researchesRepository.getResearch(params.id)

    data class Params(val id: Long)
}