package com.zakrodionov.protovary.app.ui.product

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.data.entity.ProductDetail
import com.zakrodionov.protovary.domain.interactor.product.ActionFavoriteUseCase
import com.zakrodionov.protovary.domain.interactor.product.GetProductUseCase
import com.zakrodionov.protovary.domain.interactor.product.ProductIsFavoriteUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    val getProductUseCase: GetProductUseCase,
    val actionFavoriteUseCase: ActionFavoriteUseCase,
    val productIsFavoriteUseCase: ProductIsFavoriteUseCase,
    val context: Context
) : BaseViewModel() {

    var product = MutableLiveData<ProductDetail>()
    val isFavoriteMediator = MediatorLiveData<Boolean>()

    fun loadProduct(id: Long) {
        loading.value = true

        isFavoriteMediator.addSource(productIsFavoriteUseCase.execute(ProductIsFavoriteUseCase.Params(id))) {
            isFavoriteMediator.value = it > 0
        }
        getProductUseCase.invoke(GetProductUseCase.Params(id)) { it.either(::handleFailure, ::handleProduct) }
    }

    private fun handleProduct(product: ProductDetail) {
        loading.value = false
        this.product.value = product
    }

    fun actionFavorite(id: Long) {

        CoroutineScope(Dispatchers.IO).launch {
            if (product.value != null) {
                actionFavoriteUseCase.execute(
                    ActionFavoriteUseCase.Params(
                        ProductMapper.productDetailToProduct(
                            product.value!!,
                            id
                        )
                    )
                )
            }

        }
    }


}