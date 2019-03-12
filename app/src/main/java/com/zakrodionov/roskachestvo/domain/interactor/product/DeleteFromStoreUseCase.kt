package com.zakrodionov.roskachestvo.domain.interactor.product

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.interactor.UseCaseRemote
import com.zakrodionov.roskachestvo.domain.repository.ProductRepository
import javax.inject.Inject

class DeleteFromStoreUseCase
@Inject constructor(private val productRepository: ProductRepository) {

    suspend fun execute(params: Params): Unit = productRepository.deleteFromStore(params.id)

    data class Params(val id: Long)
}