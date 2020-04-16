package com.zakrodionov.protovary.app.ui.scanner

import androidx.lifecycle.MutableLiveData
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.app.platform.Event
import com.zakrodionov.protovary.data.entity.ProductCompact
import com.zakrodionov.protovary.domain.interactor.product.ProductInteractor

class ScannerViewModel(private val productInteractor: ProductInteractor) : BaseViewModel() {

    val product = MutableLiveData<Event<ProductCompact>>()

    fun loadProduct(barcode: String) {
        launch {
            productInteractor.getProductByBarcode(barcode, ::handleProduct, ::handleState)
        }
    }

    private fun handleProduct(product: ProductCompact) {
        this.product.value = Event(product)
    }
}
