package com.zakrodionov.roskachestvo.data.repository

import androidx.lifecycle.LiveData
import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.functional.Either.Right
import com.zakrodionov.roskachestvo.app.platform.ErrorHandler
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.data.db.ProductDao
import com.zakrodionov.roskachestvo.data.db.adapter.FavoriteProductAdapter
import com.zakrodionov.roskachestvo.data.db.entity.FavoriteProduct
import com.zakrodionov.roskachestvo.data.network.Api
import com.zakrodionov.roskachestvo.domain.entity.Product
import com.zakrodionov.roskachestvo.domain.entity.ProductCompact
import com.zakrodionov.roskachestvo.domain.entity.Products
import com.zakrodionov.roskachestvo.domain.repository.ProductRepository
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

    override suspend fun getProductInfo(id: String): Either<Failure, ProductCompact> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteFromStore(id: Long) =
        productDao.deleteById(id)

    override fun getFavoriteProducts():LiveData<List<FavoriteProduct>>  =
        productDao.getFavoriteProducts()

    override fun productIsFavorite(id: Long): LiveData<Int> =
        productDao.productIsFavoriteLive(id)

    override suspend fun actionFavorite(product: Product, id: Long): Boolean =
        productDao.actionFavorite(FavoriteProductAdapter.productToStore(product, id))


}