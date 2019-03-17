package com.zakrodionov.protovary.app.ui.favorites

import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.domain.interactor.product.DeleteFromStoreUseCase
import com.zakrodionov.protovary.domain.interactor.product.DeleteFromStoreUseCase.Params
import com.zakrodionov.protovary.domain.interactor.product.GetFavoriteProductsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    val getFavoriteProductsUseCase: GetFavoriteProductsUseCase,
    val deleteFromStoresUseCase: DeleteFromStoreUseCase
) : BaseViewModel() {

    val favoriteProducts = getFavoriteProductsUseCase.execute()

    fun deleteFromStore(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            deleteFromStoresUseCase.execute(Params(id))
        }
    }
}