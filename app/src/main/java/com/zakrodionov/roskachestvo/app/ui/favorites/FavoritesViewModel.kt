package com.zakrodionov.roskachestvo.app.ui.favorites

import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.domain.interactor.product.DeleteFromStoreUseCase
import com.zakrodionov.roskachestvo.domain.interactor.product.DeleteFromStoreUseCase.*
import com.zakrodionov.roskachestvo.domain.interactor.product.GetFavoriteProductsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(getFavoriteProductsUseCase: GetFavoriteProductsUseCase, val deleteFromStoresUseCase: DeleteFromStoreUseCase) : BaseViewModel() {

    val favoriteProducts = getFavoriteProductsUseCase.execute()

    fun deleteFromStore(id: Long){
        CoroutineScope(Dispatchers.IO).launch {
            deleteFromStoresUseCase.execute(Params(id))
        }
    }
}