package com.zakrodionov.protovary.app.ui.researches

import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.data.entity.ResearchCompact
import com.zakrodionov.protovary.data.entity.Researches
import com.zakrodionov.protovary.domain.interactor.research.ResearchInteractor

class ResearchesViewModel(
    val id: Long,
    val researchInteractor: ResearchInteractor
) : BaseViewModel() {

    var sourceResearches: List<ResearchCompact> = listOf()
    var filteredResearches = MutableLiveData<List<ResearchCompact>>()
    var title = MutableLiveData<String>()
    var queryText = MutableLiveData<String>()

    init {
        loadResearchesCategory(id)
    }

    fun loadResearchesCategory(id: Long) {
        launch {
            researchInteractor.getResearchesCategory(id, ::handleResearch, ::handleState)
        }
    }

    private fun handleResearch(researches: Researches) {
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