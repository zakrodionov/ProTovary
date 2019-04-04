package com.zakrodionov.protovary.app.ui.favorites

import androidx.lifecycle.Transformations
import com.zakrodionov.protovary.app.platform.BaseViewModel
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.domain.interactor.product.ActionFavoriteUseCase
import com.zakrodionov.protovary.domain.interactor.product.ActionFavoriteUseCase.*
import com.zakrodionov.protovary.domain.interactor.product.GetFavoriteProductsUseCase
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    val actionFavoriteUseCase: ActionFavoriteUseCase,
    val productMapper: ProductMapper,
    getFavoriteProductsUseCase: GetFavoriteProductsUseCase
) : BaseViewModel() {

    val favoriteProducts = Transformations.map(getFavoriteProductsUseCase.execute())
                                        { it.map { productMapper.productFromStore(it) } }

    fun actionFavorite(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            actionFavoriteUseCase.execute(Params(product))
        }
    }
}