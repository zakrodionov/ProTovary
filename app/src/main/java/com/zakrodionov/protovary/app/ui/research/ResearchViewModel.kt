package com.zakrodionov.protovary.app.ui.research

import androidx.lifecycle.MutableLiveData
import com.snakydesign.livedataextensions.combineLatest
import com.snakydesign.livedataextensions.toMutableLiveData
import com.zakrodionov.protovary.app.ext.refresh
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.app.util.changeObservable
import com.zakrodionov.protovary.app.util.enums.ResearchFilterType.BY_DEFAULT
import com.zakrodionov.protovary.app.util.enums.ResearchSortType.*
import com.zakrodionov.protovary.data.entity.DescriptionHeader
import com.zakrodionov.protovary.data.entity.Research
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.domain.interactor.product.ProductInteractor
import com.zakrodionov.protovary.domain.model.Product

class ResearchViewModel(
    val id: Long,
    private val publishTime: Long?,
    private val productInteractor: ProductInteractor,
    private val productMapper: ProductMapper
) : BaseViewModel() {

    val researchDescription = MutableLiveData<DescriptionHeader>()

    private val sourceProducts = MutableLiveData<List<Product>>()
    private val favoriteProducts = productInteractor.getFavoriteProducts()

    val products = combineLatest(sourceProducts, favoriteProducts) { source, favorite ->
        mapAndFilterProducts(source, favorite)
    }

    var sortType by changeObservable(BY_RATING_DECREASE) { sourceProducts.refresh() }
    var filterType by changeObservable(BY_DEFAULT) { sourceProducts.refresh() }
    var queryText by changeObservable("") { sourceProducts.refresh() }

    init {
        loadResearch(id)
    }

    fun loadResearch(id: Long) {
        launch {
            productInteractor.getProductsInfo(id, ::handleProductsInfo, ::handleState)
        }
    }

    private fun handleProductsInfo(research: Research) {
        if (!research.anons.isNullOrBlank()) {
            researchDescription.value = DescriptionHeader(publishTime, research.anons)
        }

        sourceProducts.value = productMapper.mapToProducts(research.productInfo)
    }

    private fun mapAndFilterProducts(
        sourceProducts: List<Product>?,
        favoriteProducts: List<Product>?
    ): List<Product>? {
        val mappedProducts = sourceProducts?.map { sourceProduct ->
            sourceProduct.copy(isFavorite = favoriteProducts?.find { sourceProduct.id == it.id } != null)
        }

        val filteredList = mappedProducts
            ?.filter {
                it.name.contains(queryText, true)
                        || it.trademark.contains(queryText, true)
            }
            ?.filter { it.status == filterType.value }
            ?.let {
                when (sortType) {
                    BY_RATING_DECREASE -> it.sortedByDescending { it.points }
                    BY_RATING_INCREASE -> it.sortedBy { it.points }
                    BY_TRADEMARK -> it.sortedBy { it.trademark }
                }
            }

        return filteredList
    }

    fun actionFavorite(product: Product) {
        launch {
            productInteractor.actionFavorite(product)
        }
    }
}
