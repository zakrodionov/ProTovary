package com.zakrodionov.protovary.domain.interactor.product

import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.domain.entity.Product
import com.zakrodionov.protovary.domain.repository.ProductRepository
import javax.inject.Inject

class ActionFavoriteUseCase
@Inject constructor(private val productRepository: ProductRepository) {

    suspend fun execute(params: Params) = productRepository.actionFavorite(params.product)

    data class Params(val product: FavoriteProduct)
}