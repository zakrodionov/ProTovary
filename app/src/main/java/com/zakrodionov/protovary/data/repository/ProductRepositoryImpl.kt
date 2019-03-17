package com.zakrodionov.protovary.data.repository

import androidx.lifecycle.LiveData
import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.functional.Either.Right
import com.zakrodionov.protovary.app.platform.ErrorHandler
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.data.db.ProductDao
import com.zakrodionov.protovary.data.db.adapter.FavoriteProductAdapter
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.data.network.Api
import com.zakrodionov.protovary.domain.entity.Product
import com.zakrodionov.protovary.domain.entity.ProductCompact
import com.zakrodionov.protovary.domain.entity.Products
import com.zakrodionov.protovary.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: Api,
    private val productDao: ProductDao,
    private val errorHandler: ErrorHandler
) : ProductRepository {

    override suspend fun getProduct(id: Long): Either<Failure, Product> {
        return try {
            val result = api.getProduct(id).await()
            Right(result)
        } catch (exception: Throwable) {
            errorHandler.proceedException(exception)
        }
    }

    override suspend fun getProducts(): Either<Failure, List<Products>> {
        return try {
            val result = api.getProducts().await()
            Right(result)
        } catch (exception: Throwable) {
            errorHandler.proceedException(exception)
        }
    }

    override suspend fun getProductByBarcode(id: String): Either<Failure, ProductCompact> {
        return try {
            val result = api.getProductByBarcode(id).await()
            Right(result)
        } catch (exception: Throwable) {
            errorHandler.proceedException(exception)
        }
    }

    override suspend fun deleteFromStore(id: Long) =
        productDao.deleteById(id)

    override fun getFavoriteProducts(): LiveData<List<FavoriteProduct>> =
        productDao.getFavoriteProducts()

    override fun productIsFavorite(id: Long): LiveData<Int> =
        productDao.productIsFavoriteLive(id)

    override suspend fun actionFavorite(product: Product, id: Long): Boolean =
        productDao.actionFavorite(FavoriteProductAdapter.productToStore(product, id))


}