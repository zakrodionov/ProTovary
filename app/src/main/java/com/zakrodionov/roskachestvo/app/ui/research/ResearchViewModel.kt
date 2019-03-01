package com.zakrodionov.roskachestvo.app.ui.research

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.app.util.enums.ResearchFilterType
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType
import com.zakrodionov.roskachestvo.domain.entity.ResearchCompact
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory
import com.zakrodionov.roskachestvo.domain.interactor.product.GetResearchesCategory
import com.zakrodionov.roskachestvo.domain.interactor.product.GetResearchesCategory.*
import javax.inject.Inject

class ResearchViewModel @Inject constructor(val getResearchesCategory: GetResearchesCategory) : BaseViewModel() {

    var sourceResearches: List<ResearchCompact> = listOf()
    var filteredResearches =  MutableLiveData<List<ResearchCompact>>()

    var sortType =  MutableLiveData<ResearchSortType>()
    var filterType =  MutableLiveData<ResearchFilterType>()
    var queryText =  MutableLiveData<String>()

    val changesListener = MediatorLiveData<Unit>()

    init {
        sortType.value = ResearchSortType.BY_RATING_DECREASE
        filterType.value = ResearchFilterType.BY_DEFAULT

        changesListener.addSource(sortType, {  })
        changesListener.addSource(filterType, {  })
        changesListener.addSource(queryText, {  })
    }

    fun loadResearchesCategory(id: Long) {
        loading.value = true
        getResearchesCategory.invoke(Params(id)){ it.either(::handleFailure, ::handleResearch) }
    }

    private fun handleResearch(researchesCategory: ResearchesCategory){
        loading.value = false
        this.sourceResearches  = researchesCategory.researches ?: listOf()
        this.filteredResearches.value = researchesCategory.researches
    }


    fun queryTextChange(text: String){
        //filteredResearches.value = sourceResearches.filter { it.name?.toLowerCase()?.contains(text.toLowerCase()) ?: false}
        queryText.value = text
    }

    fun applyChanges(){

    }
}