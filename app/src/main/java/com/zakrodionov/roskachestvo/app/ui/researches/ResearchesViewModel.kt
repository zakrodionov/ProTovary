package com.zakrodionov.roskachestvo.app.ui.researches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.domain.entity.ResearchCompact
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory
import com.zakrodionov.roskachestvo.domain.interactor.product.GetResearch
import com.zakrodionov.roskachestvo.domain.interactor.product.GetResearchesCategory
import com.zakrodionov.roskachestvo.domain.interactor.product.GetResearchesCategory.*
import javax.inject.Inject

class ResearchesViewModel @Inject constructor(val getResearchesCategory: GetResearchesCategory) : BaseViewModel() {

    var researches = MutableLiveData<ResearchesCategory>()

    fun loadResearchesCategory(id: Long) {
        loading.value = true
        getResearchesCategory.invoke(Params(id)){ it.either(::handleFailure, ::handleResearch) }
    }

    private fun handleResearch(researches: ResearchesCategory){
        loading.value = false
        this.researches.value = researches
    }


}