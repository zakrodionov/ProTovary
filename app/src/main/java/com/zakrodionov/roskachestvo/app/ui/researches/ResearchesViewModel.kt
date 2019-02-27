package com.zakrodionov.roskachestvo.app.ui.researches

import androidx.lifecycle.MutableLiveData
import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.domain.entity.ResearchCompact
import com.zakrodionov.roskachestvo.domain.interactor.product.GetResearch
import javax.inject.Inject

class ResearchesViewModel @Inject constructor(val getResearch: GetResearch) : BaseViewModel() {

    val researches = MutableLiveData<List<ResearchCompact>>()

//    fun loadResearch(id: Long) {
//        loading.value = true
//        getResearch.invoke(Params(id)){ it.either(::handleFailure, ::handleResearch) }
//    }
//
//    private fun handleResearch(research: Research){
//        loading.value = false
//        this.research.value = research
//        Log.d("inet", research.toString())
//    }

    fun setResearches(researches: List<ResearchCompact>) {
        this.researches.value = researches
    }
}