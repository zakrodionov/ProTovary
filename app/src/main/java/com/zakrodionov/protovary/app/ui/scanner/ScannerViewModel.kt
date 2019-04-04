package com.zakrodionov.protovary.app.ui.scanner

import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.app.platform.SingleLiveEvent
import com.zakrodionov.protovary.data.entity.ProductCompact
import com.zakrodionov.protovary.domain.interactor.product.GetProductByBarcodeUseCase
import com.zakrodionov.protovary.domain.interactor.product.GetProductByBarcodeUseCase.Params
import javax.inject.Inject

class ScannerViewModel @Inject constructor(val getProductByBarcodeUseCase: GetProductByBarcodeUseCase) :
    BaseViewModel() {

    val product = SingleLiveEvent<ProductCompact>()

    fun loadProduct(barcode: String) {
        loading.value = true
        getProductByBarcodeUseCase.invoke(Params(barcode)) { it.either(::handleFailure, ::handleProduct) }
    }

    private fun handleProduct(product: ProductCompact?) {
        loading.value = false
        this.product.value = product
    }

}