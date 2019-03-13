package com.zakrodionov.roskachestvo.app.ui.product

import android.content.Context
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.domain.entity.Product
import com.zakrodionov.roskachestvo.domain.interactor.product.ActionFavoriteUseCase
import com.zakrodionov.roskachestvo.domain.interactor.product.GetProductUseCase
import com.zakrodionov.roskachestvo.domain.interactor.product.ProductIsFavoriteUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductViewModel @Inject constructor(
    val getProductUseCase: GetProductUseCase,
    val actionFavoriteUseCase: ActionFavoriteUseCase,
    val productIsFavoriteUseCase: ProductIsFavoriteUseCase,
    val context: Context
) : BaseViewModel() {

    var product = MutableLiveData<Product>()
    val isFavoriteMediator = MediatorLiveData<Boolean>()

    fun loadProduct(id: Long) {
        loading.value = true

        isFavoriteMediator.addSource(productIsFavoriteUseCase.execute(ProductIsFavoriteUseCase.Params(id))) { isFavoriteMediator.value = it > 0 }
        getProductUseCase.invoke(GetProductUseCase.Params(id)) { it.either(::handleFailure, ::handleProduct) }
    }

    private fun handleProduct(product: Product) {
        loading.value = false
        this.product.value = product
    }

    fun actionFavorite(id: Long) {

        CoroutineScope(Dispatchers.IO).launch {
            if (product.value != null) {
                val flag = actionFavoriteUseCase.execute(ActionFavoriteUseCase.Params(product.value!!, id))

                withContext(Main) {
                    if (flag)
                        message.value = context.getString(R.string.added_to_favorites)
                    else
                        message.value = context.getString(R.string.removed_from_favorites)
                }
            }

        }
    }


}