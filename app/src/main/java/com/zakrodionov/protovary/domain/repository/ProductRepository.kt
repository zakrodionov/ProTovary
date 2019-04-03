package com.zakrodionov.protovary.domain.repository

import androidx.lifecycle.LiveData
import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.domain.entity.Product
import com.zakrodionov.protovary.domain.entity.ProductCompact
import com.zakrodionov.protovary.domain.entity.ProductInfo
import com.zakrodionov.protovary.domain.entity.Products

interface ProductRepository {

    suspend fun getProduct(id: Long): Either<Failure, Product>

    suspend fun getProductByBarcode(id: String): Either<Failure, ProductCompact>

    suspend fun getProducts(): Either<Failure, List<Products>>

    suspend fun getProductsInfo(id: Long): Either<Failure, LiveData<List<ProductInfo>>>

    suspend fun actionFavorite(product: FavoriteProduct)

    fun getFavoriteProducts(): LiveData<List<FavoriteProduct>>

    fun productIsFavorite(id: Long): LiveData<Int>

}