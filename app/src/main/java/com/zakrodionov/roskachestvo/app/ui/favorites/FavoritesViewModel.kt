package com.zakrodionov.roskachestvo.app.ui.favorites

import com.zakrodionov.roskachestvo.app.platform.BaseViewModel
import com.zakrodionov.roskachestvo.data.db.ProductDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(val productDao: ProductDao) : BaseViewModel() {

    val favoriteProducts = productDao.getFavoriteProducts()

    fun deleteFromStore(id: Long){
        CoroutineScope(Dispatchers.IO).launch {
            productDao.deleteById(id)
        }
    }
}