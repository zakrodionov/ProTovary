package com.zakrodionov.protovary.data.repository

import androidx.lifecycle.LiveData
import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.functional.Either.Right
import com.zakrodionov.protovary.app.platform.ErrorHandler
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.data.db.ProductDao
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.data.network.Api
import com.zakrodionov.protovary.data.entity.ProductDetail
import com.zakrodionov.protovary.data.entity.ProductCompact
import com.zakrodionov.protovary.data.entity.ProductInfo
import com.zakrodionov.protovary.data.entity.ProductDto
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.domain.model.Product
import com.zakrodionov.protovary.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: Api,
    private val productDao: ProductDao,
    private val errorHandler: ErrorHandler
) : ProductRepository {

    override suspend fun getProduct(id: Long): Either<Failure, ProductDetail> {
        return try {
            val result = api.getProduct(id).await()
            Right(result)
        } catch (exception: Throwable) {
            errorHandler.proceedException(exception)
        }
    }

    override suspend fun getProductsDto(): Either<Failure, List<ProductDto>> {
        return try {
            val result = api.getProducts().await()
            Right(result)
        } catch (exception: Throwable) {
            errorHandler.proceedException(exception)
        }
    }

    override suspend fun getProductByBarcode(barcode: String): Either<Failure, ProductCompact> {
        return try {
            val result = api.getProductByBarcode(barcode).await()
            Right(result)
        } catch (exception: Throwable) {
            errorHandler.proceedException(exception, specialBarcodeFailureHandler = { barcode })
        }
    }

    override fun getFavoriteProducts(): LiveData<List<FavoriteProduct>> =
        productDao.getFavoriteProducts()

    override fun productIsFavorite(id: Long): LiveData<Int> =
        productDao.productIsFavoriteLive(id)

    override suspend fun actionFavorite(product: Product)  =
        productDao.actionFavorite(ProductMapper.productToStore(product))


    override suspend fun getProductsInfo(id: Long): Either<Failure, LiveData<List<ProductInfo>>> {
        return try {
            val result = api.getResearch(id).await()
            productDao.refreshProducts(result.productInfo ?: listOf())
            Right(productDao.getProducts())

        } catch (exception: Throwable) {
            errorHandler.proceedException(exception)
        }
    }
}