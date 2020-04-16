package com.zakrodionov.protovary.app.ui.research

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.app.ui.research.adapter.ResearchDescriptionItem
import com.zakrodionov.protovary.app.util.enums.ResearchFilterType
import com.zakrodionov.protovary.app.util.enums.ResearchFilterType.*
import com.zakrodionov.protovary.app.util.enums.ResearchSortType
import com.zakrodionov.protovary.app.util.enums.ResearchSortType.*
import com.zakrodionov.protovary.data.entity.ProductInfo
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.domain.interactor.product.ProductInteractor
import com.zakrodionov.protovary.domain.model.Product

class ResearchViewModel(
    val id: Long,
    private val productInteractor: ProductInteractor,
    private val productMapper: ProductMapper,
    val context: Context
) : BaseViewModel() {

    private var sourceProducts: List<Product>? = null
    val filteredProducts = MutableLiveData<List<Product>>()

    var sortType = MutableLiveData<ResearchSortType>()
    var filterType = MutableLiveData<ResearchFilterType>()
    var queryText = MutableLiveData<String>()

    val changesListener = MediatorLiveData<Unit>()
    val productsMediator = MediatorLiveData<Unit>()

    val researchDescription = MutableLiveData<List<ResearchDescriptionItem>>()

    init {
        sortType.value = BY_RATING_DECREASE
        filterType.value = BY_DEFAULT

        changesListener.addSource(sortType) { changesListener.value = Unit }
        changesListener.addSource(filterType) { changesListener.value = Unit }
        changesListener.addSource(queryText) { changesListener.value = Unit }

        loadResearch(id)
    }

    fun loadResearch(id: Long) {
        launch {
            productInteractor.getProductsInfo(id, ::handleProducts, ::handleState)
        }
    }

    private fun handleProducts(data: Pair<LiveData<List<ProductInfo>>, String?>) {
        productsMediator.removeSource(data.first)
        productsMediator.addSource(data.first) {
            sourceProducts = it.map { productMapper.productInfoToProduct(it) }
            applyChanges()
        }

        val desc = data.second
        if (!desc.isNullOrBlank()) {
            researchDescription.value = listOf(ResearchDescriptionItem(desc))
        }
    }

    fun applyChanges() {
        if (sourceProducts == null) {
            return
        }

        val list = sourceProducts!!.toMutableList()

        when (filterType.value) {
            BY_QUALITY_MARK -> {
                list.retainAll { it.status == context.getString(R.string.status_sign) }
            }
            BY_PRODUCT_WITH_VIOLATION -> {
                list.retainAll { it.status == context.getString(R.string.status_violation) }
            }
            BY_DEFAULT -> { }
        }

        list.retainAll {
            it.name.toLowerCase().contains(queryText.value?.toLowerCase() ?: "") ||
                    it.trademark.toLowerCase().contains(queryText.value?.toLowerCase() ?: "")
        }

        when (sortType.value) {
            BY_RATING_DECREASE -> list.sortByDescending { it.points }
            BY_RATING_INCREASE -> list.sortBy { it.points }
            BY_TRADEMARK -> list.sortBy { it.trademark }
        }

        filteredProducts.value = list
    }

    fun actionFavorite(product: Product) {
        launch {
            productInteractor.actionFavorite(product)
        }
    }
}
