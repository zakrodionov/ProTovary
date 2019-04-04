package com.zakrodionov.protovary.domain.interactor.product

import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.data.entity.ProductCompact
import com.zakrodionov.protovary.domain.interactor.UseCase
import com.zakrodionov.protovary.domain.interactor.product.GetProductByBarcodeUseCase.Params
import com.zakrodionov.protovary.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByBarcodeUseCase
@Inject constructor(private val productRepository: ProductRepository) : UseCase<ProductCompact, Params>() {

    override suspend fun run(params: Params): Either<Failure, ProductCompact> =
        productRepository.getProductByBarcode(params.id)

    data class Params(val id: String)
}