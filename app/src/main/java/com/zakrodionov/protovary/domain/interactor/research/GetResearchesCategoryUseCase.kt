package com.zakrodionov.protovary.domain.interactor.research

import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.data.entity.Researches
import com.zakrodionov.protovary.domain.interactor.UseCase
import com.zakrodionov.protovary.domain.repository.ResearchesRepository
import javax.inject.Inject

class GetResearchesCategoryUseCase
@Inject constructor(private val researchesRepository: ResearchesRepository) :
    UseCase<List<Researches>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Researches>> =
        researchesRepository.getResearches()
}