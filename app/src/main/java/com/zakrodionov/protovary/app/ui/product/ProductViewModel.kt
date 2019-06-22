package com.zakrodionov.protovary.app.ui.product

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.data.entity.ProductDetail
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.domain.interactor.product.ProductInteractor

class ProductViewModel(
    val id: Long,
    val productInteractor: ProductInteractor,
    val productMapper: ProductMapper,
    val context: Context
) : BaseViewModel() {

    var product = MutableLiveData<ProductDetail>()
    val isFavorite = productInteractor.productIsFavorite(id)

    init {
        loadProduct(id)
    }

    fun loadProduct(id: Long) {
        launch {
            productInteractor.getProduct(id, ::handleProduct, ::handleState)
            productInteractor.productIsFavorite(id)
        }
    }

    private fun handleProduct(product: ProductDetail) {
        this.product.value = product
    }

    fun actionFavorite(id: Long) {
        launch {
            if (product.value != null) {
                val product = productMapper.productDetailToProduct(product.value!!, id)
                productInteractor.actionFavorite(product)
            }

        }
    }


}