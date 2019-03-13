package com.zakrodionov.roskachestvo.domain.interactor.product

import androidx.lifecycle.LiveData
import com.zakrodionov.roskachestvo.data.db.entity.FavoriteProduct
import com.zakrodionov.roskachestvo.domain.repository.ProductRepository
import javax.inject.Inject

class GetFavoriteProductsUseCase
@Inject constructor(private val productRepository: ProductRepository) {

    fun execute(): LiveData<List<FavoriteProduct>> = productRepository.getFavoriteProducts()
}