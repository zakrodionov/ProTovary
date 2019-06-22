package com.zakrodionov.protovary.data.repository

import com.zakrodionov.protovary.data.db.ProductDao
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.data.network.Api
import com.zakrodionov.protovary.domain.repository.BaseRepository

class ProductRepository(
    private val api: Api,
    private val productDao: ProductDao
) : BaseRepository() {

    suspend fun getProduct(id: Long) = request(api.getProduct(id))

    suspend fun getProductsDto() = request(api.getProducts())

    suspend fun getProductByBarcode(barcode: String) = request(api.getProductByBarcode(barcode))

    suspend fun actionFavorite(product: FavoriteProduct) = request(productDao.actionFavorite(product))

    suspend fun getProductsInfo(id: Long) = request(api.getResearch(id))

    fun getFavoriteProducts() =
        productDao.getFavoriteProducts()

    fun productIsFavorite(id: Long) =
        productDao.productIsFavoriteLive(id)
}