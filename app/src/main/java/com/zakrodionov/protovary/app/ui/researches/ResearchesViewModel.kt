package com.zakrodionov.protovary.app.ui.researches

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.domain.entity.ResearchCompact
import com.zakrodionov.protovary.domain.entity.Researches
import com.zakrodionov.protovary.domain.interactor.research.GetResearchesUseCase
import com.zakrodionov.protovary.domain.interactor.research.GetResearchesUseCase.Params
import javax.inject.Inject

class ResearchesViewModel @Inject constructor(val getResearchesUseCase: GetResearchesUseCase) :
    BaseViewModel() {

    var sourceResearches: List<ResearchCompact> = listOf()
    var filteredResearches = MutableLiveData<List<ResearchCompact>>()


    fun loadResearchesCategory(id: Long) {
        loading.value = true
        getResearchesUseCase.invoke(Params(id)) { it.either(::handleFailure, ::handleResearch) }
    }

    private fun handleResearch(researches: Researches) {
        loading.value = false
        this.sourceResearches = researches.researches ?: listOf()
        this.filteredResearches.value = researches.researches
    }


    fun setQueryText(text: String) {
        filteredResearches.value =
            sourceResearches.filter { it.name?.toLowerCase()?.contains(text.toLowerCase()) ?: false }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("dfdsf", "clear")
    }
}