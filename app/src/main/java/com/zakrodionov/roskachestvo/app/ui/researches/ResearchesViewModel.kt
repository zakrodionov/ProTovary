package com.zakrodionov.roskachestvo.app.ui.researches

import androidx.lifecycle.MutableLiveData
import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.domain.entity.ResearchCompact
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory
import com.zakrodionov.roskachestvo.domain.interactor.research.GetResearchesCategory
import com.zakrodionov.roskachestvo.domain.interactor.research.GetResearchesCategory.Params
import javax.inject.Inject

class ResearchesViewModel @Inject constructor(val getResearchesCategory: GetResearchesCategory) : BaseViewModel() {

    var sourceResearches: List<ResearchCompact> = listOf()
    var filteredResearches = MutableLiveData<List<ResearchCompact>>()


    fun loadResearchesCategory(id: Long) {
        loading.value = true
        getResearchesCategory.invoke(Params(id)) { it.either(::handleFailure, ::handleResearch) }
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