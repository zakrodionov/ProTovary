package com.zakrodionov.protovary.domain.interactor.product

import androidx.lifecycle.map
import androidx.sqlite.db.SimpleSQLiteQuery
import com.zakrodionov.protovary.app.platform.ErrorHandler
import com.zakrodionov.protovary.app.platform.State
import com.zakrodionov.protovary.data.db.ProductDao
import com.zakrodionov.protovary.data.entity.ProductCompact
import com.zakrodionov.protovary.data.entity.ProductDetail
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.data.repository.ProductRepository
import com.zakrodionov.protovary.domain.interactor.BaseInteractor
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.coroutines.flow.map

class ProductInteractor(
    private val productDao: ProductDao,
    private val productRepository: ProductRepository,
    private val productMapper: ProductMapper,
    errorHandler: ErrorHandler
) : BaseInteractor(errorHandler) {

    suspend fun getProduct(
        id: Long,
        onSuccess: (ProductDetail) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onSuccess, onState) {
            productRepository.getProduct(id)
        }
    }

    suspend fun getProductsInfo(
        id: Long,
        onSuccess: (String?) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onSuccess, onState) {
            val result = productRepository.getProductsInfo(id)
            // Обновляем бд
            productDao.refreshProducts(result.productInfo ?: listOf())
            result.anons
        }
    }

    suspend fun getProductByBarcode(
        id: String,
        onSuccess: (ProductCompact) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(
            onSuccess, onState, {
                val result = productRepository.getProductByBarcode(id)
                result[0]
            },
            specialBarcodeErrorHandler = id
        )
    }

    suspend fun actionFavorite(
        product_: Product,
        onSuccess: (Unit) -> Unit = {},
        onState: (State) -> Unit = {}
    ) =
        execute(onSuccess, onState) {
            val product = productMapper.productToStore(product_)
            productRepository.actionFavorite(product)
        }

    /*DB*/
    fun observeProduct(rawQuery: SimpleSQLiteQuery) =
        productDao
            .observeProducts(rawQuery)
            .map { productMapper.mapToProducts(it) }

    fun getFavoriteProducts() =
        productRepository.getFavoriteProducts()
            .map {
                productMapper.mapProductsFromStore(it)
            }

    fun observeProductIsFavorite(id: Long) =
        productRepository.productIsFavorite(id)
            .map { it > 0 }
}
