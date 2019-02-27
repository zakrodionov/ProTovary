package com.zakrodionov.roskachestvo.domain.interactor.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory
import com.zakrodionov.roskachestvo.domain.interactor.UseCase
import com.zakrodionov.roskachestvo.domain.repository.ResearchesRepository
import javax.inject.Inject

class GetResearchesCategory
@Inject constructor(private val researchesRepository: ResearchesRepository) : UseCase<ResearchesCategory, GetResearchesCategory.Params>() {

    override suspend fun run(params: Params): Either<Failure, ResearchesCategory> = researchesRepository.getResearchesCategory(params.id)

    data class Params(val id: Long)
}