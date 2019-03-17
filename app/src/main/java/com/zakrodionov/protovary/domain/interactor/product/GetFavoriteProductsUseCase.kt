package com.zakrodionov.protovary.domain.interactor.product

import androidx.lifecycle.LiveData
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.domain.repository.ProductRepository
import javax.inject.Inject

class GetFavoriteProductsUseCase
@Inject constructor(private val productRepository: ProductRepository) {

    fun execute(): LiveData<List<FavoriteProduct>> = productRepository.getFavoriteProducts()
}