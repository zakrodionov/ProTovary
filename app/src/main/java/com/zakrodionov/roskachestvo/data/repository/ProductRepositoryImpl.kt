package com.zakrodionov.roskachestvo.data.repository

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.functional.Either.Right
import com.zakrodionov.roskachestvo.app.platform.ErrorHandler
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.data.db.ProductDao
import com.zakrodionov.roskachestvo.data.network.Api
import com.zakrodionov.roskachestvo.domain.entity.Product
import com.zakrodionov.roskachestvo.domain.entity.ProductInfo
import com.zakrodionov.roskachestvo.domain.entity.Products
import com.zakrodionov.roskachestvo.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: Api,
    private val productDao: ProductDao,
    private val errorHandler: ErrorHandler
) : ProductRepository {

    override suspend fun getProduct(id: String): Either<Failure, List<Product>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getProductInfo(id: String): Either<Failure, List<ProductInfo>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getProducts(): Either<Failure, List<Products>> {
        return try {
            val result = api.getProducts().await()
            val data = result //тут мап
            //тут вставляем в дб
            Right(data)
        } catch (exception: Throwable) {
            errorHandler.proceedException(exception) //тут функция апдеета из дб
        }
    }
}