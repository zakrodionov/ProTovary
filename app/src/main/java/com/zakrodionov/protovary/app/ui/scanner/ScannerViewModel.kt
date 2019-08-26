package com.zakrodionov.protovary.app.ui.scanner

import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.app.platform.SingleLiveEvent
import com.zakrodionov.protovary.data.entity.ProductCompact
import com.zakrodionov.protovary.domain.interactor.product.ProductInteractor

class ScannerViewModel(val productInteractor: ProductInteractor) :
    BaseViewModel() {

    val product = SingleLiveEvent<ProductCompact>()

    fun loadProduct(barcode: String) {
        launch {
            productInteractor.getProductByBarcode(barcode, ::handleProduct, ::handleState)
        }
    }

    private fun handleProduct(product: ProductCompact?) {
        this.product.value = product
    }

}