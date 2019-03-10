package com.zakrodionov.roskachestvo.domain.interactor.research

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.entity.Research
import com.zakrodionov.roskachestvo.domain.interactor.UseCase
import com.zakrodionov.roskachestvo.domain.interactor.research.GetResearch.Params
import com.zakrodionov.roskachestvo.domain.repository.ResearchesRepository
import javax.inject.Inject

class GetResearch
@Inject constructor(private val researchesRepository: ResearchesRepository) : UseCase<Research, Params>() {

    override suspend fun run(params: Params): Either<Failure, Research> = researchesRepository.getResearch(params.id)

    data class Params(val id: Long)
}