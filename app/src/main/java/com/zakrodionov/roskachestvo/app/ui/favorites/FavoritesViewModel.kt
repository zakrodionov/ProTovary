package com.zakrodionov.roskachestvo.app.ui.favorites

import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.data.db.ProductDao
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(productDao: ProductDao) : BaseViewModel() {

    val favoriteProducts = productDao.getFavoriteProducts()

}