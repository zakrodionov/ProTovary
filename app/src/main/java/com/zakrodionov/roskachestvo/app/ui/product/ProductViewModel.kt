package com.zakrodionov.roskachestvo.app.ui.product

import androidx.lifecycle.MutableLiveData
import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.domain.entity.*
import com.zakrodionov.roskachestvo.domain.interactor.product.GetProduct
import com.zakrodionov.roskachestvo.domain.interactor.research.GetResearch
import javax.inject.Inject

class ProductViewModel @Inject constructor(val getProduct: GetProduct) : BaseViewModel() {

    var product =  MutableLiveData<Product>()


    fun loadProduct(id: Long) {
        loading.value = true
        getProduct.invoke(GetProduct.Params(id)){ it.either(::handleFailure, ::handleProduct) }
    }

    private fun handleProduct(_product: Product){
        loading.value = false
        product.value = _product
    }
}