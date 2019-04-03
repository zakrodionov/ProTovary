package com.zakrodionov.protovary.domain.interactor.product

import androidx.lifecycle.LiveData
import com.zakrodionov.protovary.app.functional.Either
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.domain.entity.ProductInfo
import com.zakrodionov.protovary.domain.interactor.UseCase
import com.zakrodionov.protovary.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductsInfoUseCase
@Inject constructor(private val productRepository: ProductRepository) :
    UseCase<LiveData<List<ProductInfo>>, GetProductsInfoUseCase.Params>() {

    override suspend fun run(params: Params): Either<Failure, LiveData<List<ProductInfo>>> =
        productRepository.getProductsInfo(params.id)

    data class Params(val id: Long)
}