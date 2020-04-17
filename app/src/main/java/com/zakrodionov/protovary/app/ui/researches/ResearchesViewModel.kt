package com.zakrodionov.protovary.app.ui.researches

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.app.util.changeObservable
import com.zakrodionov.protovary.data.entity.ResearchCompact
import com.zakrodionov.protovary.data.entity.Researches
import com.zakrodionov.protovary.domain.interactor.research.ResearchInteractor

class ResearchesViewModel(
    val id: Long,
    private val researchInteractor: ResearchInteractor
) : BaseViewModel() {

    private val sourceResearches = mutableListOf<ResearchCompact>()

    val filteredResearches = MutableLiveData<List<ResearchCompact>>()
    val title = MutableLiveData<String>()

    var queryText by changeObservable("") { applyQueryText() }

    init {
        loadResearchesCategory(id)
    }

    fun loadResearchesCategory(id: Long) {
        launch {
            researchInteractor.getResearchesCategory(id, ::handleResearch, ::handleState)
        }
    }

    private fun handleResearch(researches: Researches) {
        title.value = researches.name
        sourceResearches.clear()
        sourceResearches.addAll(researches.researches?.sortedBy { it.name } ?: listOf())
        applyQueryText()
    }

    @SuppressLint("DefaultLocale")
    fun applyQueryText() {
        val text = queryText.toLowerCase()
        filteredResearches.value =
            sourceResearches.filter { it.name?.toLowerCase()?.contains(text) ?: false }
    }
}
