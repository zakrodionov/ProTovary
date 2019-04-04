package com.zakrodionov.protovary.domain.repository

import androidx.lifecycle.LiveData
import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.data.entity.ProductDetail
import com.zakrodionov.protovary.data.entity.ProductCompact
import com.zakrodionov.protovary.data.entity.ProductInfo
import com.zakrodionov.protovary.data.entity.ProductDto
import com.zakrodionov.protovary.domain.model.Product

interface ProductRepository {

    suspend fun getProduct(id: Long): Either<Failure, ProductDetail>

    suspend fun getProductByBarcode(barcode: String): Either<Failure, ProductCompact>

    suspend fun getProductsDto(): Either<Failure, List<ProductDto>>

    suspend fun getProductsInfo(id: Long): Either<Failure, LiveData<List<ProductInfo>>>

    suspend fun actionFavorite(product: Product)

    fun getFavoriteProducts(): LiveData<List<FavoriteProduct>>

    fun productIsFavorite(id: Long): LiveData<Int>

}