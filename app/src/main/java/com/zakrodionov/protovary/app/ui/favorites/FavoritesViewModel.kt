package com.zakrodionov.protovary.app.ui.favorites

import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.domain.interactor.product.ProductInteractor
import com.zakrodionov.protovary.domain.model.Product

class FavoritesViewModel(val productInteractor: ProductInteractor) : BaseViewModel() {

    var favoriteProducts = productInteractor.getFavoriteProducts()

    fun actionFavorite(product: Product) {
        launch {
            productInteractor.actionFavorite(product)
        }
    }
}