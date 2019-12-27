package com.zakrodionov.protovary.data.repository

import com.zakrodionov.protovary.data.db.ProductDao
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.data.network.Api

class ProductRepository(
    private val api: Api,
    private val productDao: ProductDao
) {

    suspend fun getProduct(id: Long) = api.getProduct(id)

    suspend fun getProductsDto() = api.getProducts()

    suspend fun getProductByBarcode(barcode: String) = api.getProductByBarcode(barcode)

    suspend fun actionFavorite(product: FavoriteProduct) = productDao.actionFavorite(product)

    suspend fun getProductsInfo(id: Long) = api.getResearch(id)

    fun getFavoriteProducts() =
        productDao.getFavoriteProducts()

    fun productIsFavorite(id: Long) =
        productDao.productIsFavoriteLive(id)
}