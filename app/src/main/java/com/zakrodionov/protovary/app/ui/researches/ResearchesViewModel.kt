package com.zakrodionov.protovary.app.ui.researches

import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.data.entity.ResearchCompact
import com.zakrodionov.protovary.data.entity.Researches
import com.zakrodionov.protovary.domain.interactor.research.GetResearchesUseCase
import com.zakrodionov.protovary.domain.interactor.research.GetResearchesUseCase.Params
import javax.inject.Inject

class ResearchesViewModel @Inject constructor(val getResearchesUseCase: GetResearchesUseCase) : BaseViewModel() {

    var sourceResearches: List<ResearchCompact> = listOf()
    var filteredResearches = MutableLiveData<List<ResearchCompact>>()
    var title = MutableLiveData<String>()
    var queryText = MutableLiveData<String>()


    fun loadResearchesCategory(id: Long) {
        loading.value = true
        getResearchesUseCase.invoke(Params(id)) { it.either(::handleFailure, ::handleResearch) }
    }

    private fun handleResearch(researches: Researches) {
        loading.value = false
        this.title.value = researches.name
        this.sourceResearches = researches.researches?.sortedBy { it.name } ?: listOf()
        applyQueryText()
    }


    fun applyQueryText() {
        val text = queryText.value?.toLowerCase() ?: ""
        filteredResearches.value =
            sourceResearches.filter { it.name?.toLowerCase()?.contains(text) ?: false }
    }

}