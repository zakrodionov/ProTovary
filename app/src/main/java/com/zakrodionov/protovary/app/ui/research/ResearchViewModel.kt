package com.zakrodionov.protovary.app.ui.research

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.app.util.enums.ResearchFilterType
import com.zakrodionov.protovary.app.util.enums.ResearchFilterType.*
import com.zakrodionov.protovary.app.util.enums.ResearchSortType
import com.zakrodionov.protovary.app.util.enums.ResearchSortType.*
import com.zakrodionov.protovary.domain.entity.ProductInfo
import com.zakrodionov.protovary.domain.entity.Research
import com.zakrodionov.protovary.domain.interactor.research.GetResearchUseCase
import com.zakrodionov.protovary.domain.interactor.research.GetResearchUseCase.Params
import javax.inject.Inject

class ResearchViewModel @Inject constructor(val getResearchUseCase: GetResearchUseCase, val context: Context) :
    BaseViewModel() {

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
            BY_QUALITY_MARK -> {
                list.retainAll { it.status == context.getString(R.string.status_sign) }
            }
            BY_PRODUCT_WITH_VIOLATION -> {
                list.retainAll { it.status == context.getString(R.string.status_violation) }
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