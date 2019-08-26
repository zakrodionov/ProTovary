package com.zakrodionov.protovary.app.ui.researchescategory

import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.data.entity.Researches
import com.zakrodionov.protovary.domain.interactor.research.ResearchInteractor

class ResearchesCategoryViewModel(private val researchInteractor: ResearchInteractor) : BaseViewModel() {

    val researches = MutableLiveData<List<Researches>>()

    init {
        loadResearches()
    }

    fun loadResearches() {
        launch {
            researchInteractor.getResearches(::handleResearches, ::handleState)
        }
    }

    private fun handleResearches(list: List<Researches>) {
        researches.value = list.sortedBy { it.name }
    }
}