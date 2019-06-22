package com.zakrodionov.protovary.app.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.domain.interactor.product.ProductInteractor
import com.zakrodionov.protovary.domain.model.Product

class FavoritesViewModel(val productInteractor: ProductInteractor, val productMapper: ProductMapper) : BaseViewModel() {

    var favoriteProducts = MediatorLiveData<List<Product>>()

    init {
        launch {
            productInteractor.getFavoriteProducts(::handleFavoriteProducts, ::handleState)
        }
    }

    private fun handleFavoriteProducts(favoriteProducts_: LiveData<List<FavoriteProduct>>) {
        favoriteProducts.removeSource(favoriteProducts)
        favoriteProducts.addSource(favoriteProducts_) {
            favoriteProducts.value = it.map { productMapper.productFromStore(it) }
        }
    }

    fun actionFavorite(product: Product) {
        launch {
            productInteractor.actionFavorite(product)
        }
    }
}