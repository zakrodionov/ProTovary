package com.zakrodionov.protovary.domain.interactor.product

import com.zakrodionov.protovary.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteFromStoreUseCase
@Inject constructor(private val productRepository: ProductRepository) {

    suspend fun execute(params: Params): Unit = productRepository.deleteFromStore(params.id)

    data class Params(val id: Long)
}