package com.zakrodionov.protovary.app.ui.product

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.data.entity.ProductDetail
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.domain.interactor.product.ProductInteractor

class ProductViewModel(
    val id: Long,
    private val productInteractor: ProductInteractor,
    private val productMapper: ProductMapper,
    val context: Context
) : BaseViewModel() {

    val product = MutableLiveData<ProductDetail>()
    val isFavorite = productInteractor.observeProductIsFavorite(id)

    init {
        loadProduct(id)
    }

    fun loadProduct(id: Long) {
        launch {
            productInteractor.getProduct(id, ::handleProduct, ::handleState)
        }
    }

    private fun handleProduct(product: ProductDetail) {
        this.product.value = product
    }

    fun actionFavorite(id: Long) {
        launch {
            product.value?.let {
                val product = productMapper.productDetailToProduct(it, id)
                productInteractor.actionFavorite(product)
            }
        }
    }
}
