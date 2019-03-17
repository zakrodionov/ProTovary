package com.zakrodionov.protovary.app.ui.researches

import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.domain.entity.ResearchCompact
import com.zakrodionov.protovary.domain.entity.ResearchesCategory
import com.zakrodionov.protovary.domain.interactor.research.GetResearchesCategoryUseCase
import com.zakrodionov.protovary.domain.interactor.research.GetResearchesCategoryUseCase.Params
import javax.inject.Inject

class ResearchesViewModel @Inject constructor(val getResearchesCategoryUseCase: GetResearchesCategoryUseCase) :
    BaseViewModel() {

    var sourceResearches: List<ResearchCompact> = listOf()
    var filteredResearches = MutableLiveData<List<ResearchCompact>>()


    fun loadResearchesCategory(id: Long) {
        loading.value = true
        getResearchesCategoryUseCase.invoke(Params(id)) { it.either(::handleFailure, ::handleResearch) }
    }

    private fun handleResearch(researchesCategory: ResearchesCategory) {
        loading.value = false
        this.sourceResearches = researchesCategory.researches ?: listOf()
        this.filteredResearches.value = researchesCategory.researches
    }


    fun setQueryText(text: String) {
        filteredResearches.value =
            sourceResearches.filter { it.name?.toLowerCase()?.contains(text.toLowerCase()) ?: false }
    }
}