package com.zakrodionov.protovary.app.ui.research

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.app.util.changeObservable
import com.zakrodionov.protovary.app.util.enums.ResearchFilterType.BY_DEFAULT
import com.zakrodionov.protovary.app.util.enums.ResearchSortType.BY_RATING_DECREASE
import com.zakrodionov.protovary.domain.interactor.product.ProductInteractor
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect

class ResearchViewModel(
    val id: Long,
    private val productInteractor: ProductInteractor,
    val context: Context
) : BaseViewModel() {

    val researchDescription = MutableLiveData<List<String>>()
    val products = MutableLiveData<List<Product>>()

    var sortType by changeObservable(BY_RATING_DECREASE) { applyFilters() }
    var filterType by changeObservable(BY_DEFAULT) { applyFilters() }
    var queryText by changeObservable("") { applyFilters() }

    private var collectJob: Job? = null

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
            researchDescription.value = listOf(info)
        }
        applyFilters()
    }

    private fun applyFilters() {
        collectJob?.cancel()
        collectJob = launch {
            val rawQuery = SimpleSQLiteQuery(
                "SELECT * FROM productinfo WHERE name LIKE '%$queryText%'" +
                        " AND IFNULL(status, '') = '${filterType.value}'" +
                        " ORDER BY ${sortType.value} ${sortType.direction}"
            )

            productInteractor.observeProduct(rawQuery)
                .collect {
                    products.value = it
                }
        }
    }

    fun actionFavorite(product: Product) {
        launch {
            productInteractor.actionFavorite(product)
        }
    }
}
