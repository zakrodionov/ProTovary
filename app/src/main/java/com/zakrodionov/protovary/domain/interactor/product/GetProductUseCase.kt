package com.zakrodionov.protovary.domain.interactor.product

import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.data.entity.ProductDetail
import com.zakrodionov.protovary.domain.interactor.UseCase
import com.zakrodionov.protovary.domain.interactor.product.GetProductUseCase.Params
import com.zakrodionov.protovary.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase
@Inject constructor(private val productRepository: ProductRepository) : UseCase<ProductDetail, Params>() {

    override suspend fun run(params: Params): Either<Failure, ProductDetail> = productRepository.getProduct(params.id)

    data class Params(val id: Long)
}