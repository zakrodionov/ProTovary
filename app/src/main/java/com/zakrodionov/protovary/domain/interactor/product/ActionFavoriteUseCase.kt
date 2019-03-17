package com.zakrodionov.protovary.domain.interactor.product

import com.zakrodionov.protovary.domain.entity.Product
import com.zakrodionov.protovary.domain.repository.ProductRepository
import javax.inject.Inject

class ActionFavoriteUseCase
@Inject constructor(private val productRepository: ProductRepository) {

    suspend fun execute(params: Params): Boolean = productRepository.actionFavorite(params.product, params.id)

    data class Params(val product: Product, val id: Long)
}