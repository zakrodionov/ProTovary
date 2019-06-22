package com.zakrodionov.protovary.domain.interactor.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.zakrodionov.protovary.app.platform.ErrorHandler
import com.zakrodionov.protovary.app.platform.State
import com.zakrodionov.protovary.data.db.ProductDao
import com.zakrodionov.protovary.data.entity.ProductCompact
import com.zakrodionov.protovary.data.entity.ProductDetail
import com.zakrodionov.protovary.data.entity.ProductDto
import com.zakrodionov.protovary.data.entity.ProductInfo
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.data.repository.ProductRepository
import com.zakrodionov.protovary.domain.interactor.BaseInteractor
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductInteractor(
    private val productDao: ProductDao,
    private val productRepository: ProductRepository,
    val productMapper: ProductMapper,
    errorHandler: ErrorHandler
) : BaseInteractor(errorHandler) {


    suspend fun getProduct(
        id: Long,
        onSuccess: (ProductDetail) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onState) {
            val result = productRepository.getProduct(id)
            onSuccess.invoke(result)
        }
    }

    suspend fun getProductsInfo(
        id: Long,
        onSuccess: (LiveData<List<ProductInfo>>) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onState) {
            val result = productRepository.getProductsInfo(id)

            withContext(Dispatchers.IO) {
                productDao.refreshProducts(result.productInfo ?: listOf())
            }

            onSuccess.invoke(productDao.getProducts())
        }
    }

    suspend fun productIsFavorite(
        onSuccess: (List<ProductDto>) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onState) {
            val result = productRepository.getProductsDto()
            onSuccess.invoke(result)
        }
    }

    suspend fun getProductByBarcode(
        id: String,
        onSuccess: (ProductCompact) -> Unit,
        onState: (State) -> Unit
    ) {
        execute(onState) {
            val result = productRepository.getProductByBarcode(id)
            onSuccess.invoke(result)
        }
    }


    suspend fun actionFavorite(
        product_: Product,
        onSuccess: (Unit) -> Unit = {},
        onState: (State) -> Unit = {}
    ) =
        execute(onState) {
            val product = productMapper.productToStore(product_)
            val result = productRepository.actionFavorite(product)
            onSuccess.invoke(result)
        }


    /*DB*/
    fun getFavoriteProducts() = Transformations.map(productRepository.getFavoriteProducts()) { it.map { productMapper.productFromStore(it) } }

    fun productIsFavorite(id: Long) = Transformations.map(productRepository.productIsFavorite(id)) { it > 0 }

}