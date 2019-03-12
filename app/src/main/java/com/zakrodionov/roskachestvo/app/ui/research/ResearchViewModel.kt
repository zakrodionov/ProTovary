package com.zakrodionov.roskachestvo.app.ui.research

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.app.util.enums.ResearchFilterType
import com.zakrodionov.roskachestvo.app.util.enums.ResearchFilterType.*
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType.*
import com.zakrodionov.roskachestvo.domain.entity.ProductInfo
import com.zakrodionov.roskachestvo.domain.entity.Research
import com.zakrodionov.roskachestvo.domain.interactor.research.GetResearchUseCase
import com.zakrodionov.roskachestvo.domain.interactor.research.GetResearchUseCase.Params
import javax.inject.Inject

class ResearchViewModel @Inject constructor(val getResearchUseCase: GetResearchUseCase) : BaseViewModel() {

    var sourceProducts: List<ProductInfo> = listOf()
    var filteredProducts = MutableLiveData<List<ProductInfo>>()

    var sortType = MutableLiveData<ResearchSortType>()
    var filterType = MutableLiveData<ResearchFilterType>()
    var queryText = MutableLiveData<String>()

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
        getResearchUseCase.invoke(Params(id)) { it.either(::handleFailure, ::handleResearch) }
    }

    private fun handleResearch(research: Research) {
        loading.value = false
        this.sourceProducts = research.productInfo ?: listOf()
        applyChanges()
    }

    fun applyChanges() {
        val list = sourceProducts.toMutableList()

        when (filterType.value) {
            BY_DEFAULT -> {
            }
            QUALITY_MARK -> {
                list.retainAll { it.status == "withsign" }
            }
            PRODUCT_WITH_VIOLATION -> {
                list.retainAll { it.status == "withviolation" }
            }
        }

        list.retainAll {
            it.name?.toLowerCase()?.contains(queryText.value?.toLowerCase() ?: "") ?: false ||
                    it.trademark?.toLowerCase()?.contains(queryText.value?.toLowerCase() ?: "") ?: false
        }

        when (sortType.value) {
            BY_RATING_DECREASE -> list.sortByDescending { it.points }
            BY_RATING_INCREASE -> list.sortBy { it.points }
            BY_TRADEMARK -> list.sortBy { it.trademark }
        }

        filteredProducts.value = list
    }
}