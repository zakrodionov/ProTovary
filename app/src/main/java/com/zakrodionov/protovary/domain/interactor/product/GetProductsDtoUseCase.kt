package com.zakrodionov.protovary.domain.interactor.product

import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.data.entity.ProductDto
import com.zakrodionov.protovary.domain.interactor.UseCase
import com.zakrodionov.protovary.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsDtoUseCase
@Inject constructor(private val productRepository: ProductRepository) : UseCase<List<ProductDto>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<ProductDto>> = productRepository.getProductsDto()
}