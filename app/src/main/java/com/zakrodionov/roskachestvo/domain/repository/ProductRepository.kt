package com.zakrodionov.roskachestvo.domain.repository

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.entity.Product
import com.zakrodionov.roskachestvo.domain.entity.ProductCompact
import com.zakrodionov.roskachestvo.domain.entity.Products

interface ProductRepository {

    suspend fun getProduct(id: Long): Either<Failure, Product>

    suspend fun getProductInfo(id: String): Either<Failure, ProductCompact>

    suspend fun getProducts(): Either<Failure, List<Products>>
}