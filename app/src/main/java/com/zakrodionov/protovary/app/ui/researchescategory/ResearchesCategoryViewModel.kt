package com.zakrodionov.protovary.app.ui.researchescategory

import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.domain.entity.ResearchesCategory
import com.zakrodionov.protovary.domain.interactor.UseCase
import com.zakrodionov.protovary.domain.interactor.research.GetResearchesCategoryUseCase
import javax.inject.Inject

class ResearchesCategoryViewModel @Inject constructor(val getResearchesCategoryUseCase: GetResearchesCategoryUseCase) :
    BaseViewModel() {

    val researches = MutableLiveData<List<ResearchesCategory>>()

    fun loadResearches() {
        loading.value = true
        getResearchesCategoryUseCase.invoke(UseCase.None()) { it.either(::handleFailure, ::handleResearches) }
    }

    private fun handleResearches(list: List<ResearchesCategory>) {
        loading.value = false
        researches.value = list
    }
}