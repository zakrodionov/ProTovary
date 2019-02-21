package com.zakrodionov.roskachestvo.domain.interactor.product

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.entity.Product
import com.zakrodionov.roskachestvo.domain.interactor.UseCase
import com.zakrodionov.roskachestvo.domain.interactor.product.GetProduct.Params
import com.zakrodionov.roskachestvo.domain.repository.ProductRepository
import javax.inject.Inject

class GetProduct
@Inject constructor(private val productRepository: ProductRepository) : UseCase<List<Product>, Params>() {

    override suspend fun run(params: Params): Either<Failure, List<Product>> = productRepository.getProduct(params.id)

    data class Params(val id: String)
}