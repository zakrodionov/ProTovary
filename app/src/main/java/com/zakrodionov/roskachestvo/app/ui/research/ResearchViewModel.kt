package com.zakrodionov.roskachestvo.app.ui.research

import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.app.util.enums.ResearchFilterType
import com.zakrodionov.roskachestvo.app.util.enums.ResearchFilterType.*
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType.*
import com.zakrodionov.roskachestvo.domain.entity.*
import com.zakrodionov.roskachestvo.domain.interactor.product.GetResearch
import com.zakrodionov.roskachestvo.domain.interactor.product.GetResearchesCategory
import com.zakrodionov.roskachestvo.domain.interactor.product.GetResearchesCategory.*
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
        this.filteredResearch.value = research.productsInfo ?: listOf()
    }

    fun setFilterType(type: ResearchFilterType){
        filterType.value = type
    }

    fun setSortType(type: ResearchSortType){
        sortType.value = type
    }

    fun queryTextChange(text: String){
        queryText.value = text
    }

    fun applyChanges(){
        val list = sourceResearch.toMutableList()

        when (filterType.value){
            BY_DEFAULT -> {}
            QUALITY_MARK -> { list.retainAll { it.status == "withsign" } }
            PRODUCT_WITH_VIOLATION -> { list.retainAll { it.status == "withviolation" } }
        }

        list.retainAll { it.name?.toLowerCase()?.contains(queryText.value?.toLowerCase() ?: "") ?: false }

        when (sortType.value){
            BY_RATING_DECREASE -> list.sortByDescending { it.points }
            BY_RATING_INCREASE -> list.sortBy { it.points }
            BY_TRADEMARK -> list.sortBy { it.trademark }
        }

        filteredResearch.value = list
    }
}