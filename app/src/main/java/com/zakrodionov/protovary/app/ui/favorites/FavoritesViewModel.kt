package com.zakrodionov.protovary.app.ui.favorites

import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.domain.interactor.product.ActionFavoriteUseCase
import com.zakrodionov.protovary.domain.interactor.product.GetFavoriteProductsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    val actionFavoriteUseCase: ActionFavoriteUseCase,
    getFavoriteProductsUseCase: GetFavoriteProductsUseCase
) : BaseViewModel() {

    val favoriteProducts = getFavoriteProductsUseCase.execute()

    fun actionFavorite(product: FavoriteProduct) {
        CoroutineScope(Dispatchers.IO).launch {
            actionFavoriteUseCase.execute(ActionFavoriteUseCase.Params(product))
        }
    }
}