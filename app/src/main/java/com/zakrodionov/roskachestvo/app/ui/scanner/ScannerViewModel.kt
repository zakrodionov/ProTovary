package com.zakrodionov.roskachestvo.app.ui.scanner

import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.app.platform.SingleLiveEvent
import com.zakrodionov.roskachestvo.domain.entity.ProductCompact
import com.zakrodionov.roskachestvo.domain.interactor.product.GetProductByBarcodeUseCase
import com.zakrodionov.roskachestvo.domain.interactor.product.GetProductByBarcodeUseCase.Params
import javax.inject.Inject

class ScannerViewModel @Inject constructor(val getProductByBarcodeUseCase: GetProductByBarcodeUseCase) :
    BaseViewModel() {

    val product = SingleLiveEvent<ProductCompact>()

    fun loadProduct(id: String) {
        loading.value = true
        getProductByBarcodeUseCase.invoke(Params(id)) { it.either(::handleFailure, ::handleProduct) }
    }

    private fun handleProduct(product: ProductCompact?) {
        loading.value = false
        this.product.value = product
    }
}