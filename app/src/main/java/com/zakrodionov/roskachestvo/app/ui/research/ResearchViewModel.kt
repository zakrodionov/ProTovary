package com.zakrodionov.roskachestvo.app.ui.research

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.app.util.enums.ResearchFilterType
import com.zakrodionov.roskachestvo.app.util.enums.ResearchFilterType.*
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType.*
import com.zakrodionov.roskachestvo.domain.entity.*
import com.zakrodionov.roskachestvo.domain.interactor.research.GetResearch
import javax.inject.Inject

class ResearchViewModel @Inject constructor(val getResearch: GetResearch) : BaseViewModel() {

    var sourceResearch: List<ProductsInfo> = listOf()
    var filteredResearch =  MutableLiveData<List<ProductsInfo>>()

    var sortType =  MutableLiveData<ResearchSortType>()
    var filterType =  MutableLiveData<ResearchFilterType>()
    var queryText =  MutableLiveData<String>()

    val changesListener = MediatorLiveData<Unit>()

    init {
        sortType.value = BY_RATING_DECREASE
        filterType.value = BY_DEFAULT

        changesListener.addSource(sortType) { changesListener.value = Unit }
        changesListener.addSource(filterType) { changesListener.value = Unit }
        changesListener.addSource(queryText) { changesListener.value = Unit }
    }

    fun loadResearch(id: Long) {
        loading.value = true
        getResearch.invoke(GetResearch.Params(id)){ it.either(::handleFailure, ::handleResearch) }
    }

    private fun handleResearch(research: Research){
        loading.value = false
        this.sourceResearch  = research.productsInfo ?: listOf()
        applyChanges()
    }

    fun applyChanges(){
        val list = sourceResearch.toMutableList()

        when (filterType.value){
            BY_DEFAULT -> {}
            QUALITY_MARK -> { list.retainAll { it.status == "withsign" } }
            PRODUCT_WITH_VIOLATION -> { list.retainAll { it.status == "withviolation" } }
        }

        list.retainAll { it.name?.toLowerCase()?.contains(queryText.value?.toLowerCase() ?: "") ?: false ||
                it.trademark?.toLowerCase()?.contains(queryText.value?.toLowerCase() ?: "") ?: false
        }

        when (sortType.value){
            BY_RATING_DECREASE -> list.sortByDescending { it.points }
            BY_RATING_INCREASE -> list.sortBy { it.points }
            BY_TRADEMARK -> list.sortBy { it.trademark }
        }

        filteredResearch.value = list
    }
}