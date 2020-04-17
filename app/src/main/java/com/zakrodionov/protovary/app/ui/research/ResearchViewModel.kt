package com.zakrodionov.protovary.app.ui.research

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.app.util.changeObservable
import com.zakrodionov.protovary.app.util.enums.ResearchFilterType.*
import com.zakrodionov.protovary.app.util.enums.ResearchSortType.*
import com.zakrodionov.protovary.data.db.ProductDao
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.domain.interactor.product.ProductInteractor
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map

class ResearchViewModel(
    val id: Long,
    private val productInteractor: ProductInteractor,
    private val productMapper: ProductMapper,
    private val productDao: ProductDao,
    val context: Context
) : BaseViewModel() {

    private val productsFlow = productDao.getProducts().map { productMapper.mapToProducts(it) }
    private val sourceProducts = mutableListOf<Product>()

    val researchDescription = MutableLiveData<String>()
    val filteredProducts = MutableLiveData<List<Product>>()

    var sortType by changeObservable(BY_RATING_DECREASE) { applyFilters() }
    var filterType by changeObservable(BY_DEFAULT) { applyFilters() }
    var queryText by changeObservable("") { applyFilters() }

    init {
        loadResearch(id)
    }

    fun loadResearch(id: Long) {
        launch {
            productInteractor.getProductsInfo(id, ::handleProductsInfo, ::handleState)
        }
    }

    private fun handleProductsInfo(info: String?) {
        if (!info.isNullOrBlank()) {
            researchDescription.value = info
        }
        collectProducts()
    }

    private fun collectProducts() {
        launch {
            productsFlow.collect {
                sourceProducts.clear()
                sourceProducts.addAll(it ?: listOf())
                applyFilters()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    fun applyFilters() {
        val mutableList = sourceProducts.toMutableList()

        when (filterType) {
            BY_QUALITY_MARK -> {
                mutableList.retainAll { it.status == context.getString(R.string.status_sign) }
            }
            BY_PRODUCT_WITH_VIOLATION -> {
                mutableList.retainAll { it.status == context.getString(R.string.status_violation) }
            }
            BY_DEFAULT -> Unit
        }

        mutableList.retainAll {
            it.name.toLowerCase().contains(queryText.toLowerCase()) ||
                    it.trademark.toLowerCase().contains(queryText.toLowerCase())
        }

        when (sortType) {
            BY_RATING_DECREASE -> mutableList.sortByDescending { it.points }
            BY_RATING_INCREASE -> mutableList.sortBy { it.points }
            BY_TRADEMARK -> mutableList.sortBy { it.trademark }
        }

        filteredProducts.value = mutableList
    }

    fun actionFavorite(product: Product) {
        launch {
            productInteractor.actionFavorite(product)
        }
    }
}
