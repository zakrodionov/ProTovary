package com.zakrodionov.roskachestvo.app.ui.researchescategory

import androidx.lifecycle.MutableLiveData
import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory
import com.zakrodionov.roskachestvo.domain.interactor.UseCaseRemote
import com.zakrodionov.roskachestvo.domain.interactor.research.GetResearchesUseCase
import javax.inject.Inject

class ResearchesCategoryViewModel @Inject constructor(val getResearchesUseCase: GetResearchesUseCase) : BaseViewModel() {

    val researches = MutableLiveData<List<ResearchesCategory>>()

    fun loadResearches() {
        loading.value = true
        getResearchesUseCase.invoke(UseCaseRemote.None()) { it.either(::handleFailure, ::handleResearches) }
    }

    private fun handleResearches(list: List<ResearchesCategory>) {
        loading.value = false
        researches.value = list
    }
}