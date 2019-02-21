package com.zakrodionov.roskachestvo.domain.repository

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.entity.Product
import com.zakrodionov.roskachestvo.domain.entity.ProductInfo
import com.zakrodionov.roskachestvo.domain.entity.Products

interface ProductRepository {

    suspend fun getProduct(id: String): Either<Failure, List<Product>>

    suspend fun getProductInfo(id: String): Either<Failure, List<ProductInfo>>

    suspend fun getProducts(): Either<Failure, List<Products>>
}