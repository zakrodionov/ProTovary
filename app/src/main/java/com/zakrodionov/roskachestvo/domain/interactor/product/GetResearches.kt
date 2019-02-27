package com.zakrodionov.roskachestvo.domain.interactor.product

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory
import com.zakrodionov.roskachestvo.domain.interactor.UseCase
import com.zakrodionov.roskachestvo.domain.repository.ResearchesRepository
import javax.inject.Inject

class GetResearches
@Inject constructor(private val researchesRepository: ResearchesRepository) :
    UseCase<List<ResearchesCategory>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<ResearchesCategory>> =
        researchesRepository.getResearches()
}