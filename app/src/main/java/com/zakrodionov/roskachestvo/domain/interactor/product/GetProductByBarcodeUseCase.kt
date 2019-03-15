package com.zakrodionov.roskachestvo.domain.interactor.product

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.entity.ProductCompact
import com.zakrodionov.roskachestvo.domain.entity.Products
import com.zakrodionov.roskachestvo.domain.interactor.UseCase
import com.zakrodionov.roskachestvo.domain.interactor.product.GetProductByBarcodeUseCase.Params
import com.zakrodionov.roskachestvo.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByBarcodeUseCase
@Inject constructor(private val productRepository: ProductRepository) : UseCase<ProductCompact, Params>() {

    override suspend fun run(params: Params): Either<Failure, ProductCompact> = productRepository.getProductByBarcode(params.id)

    data class Params(val id: String)
}