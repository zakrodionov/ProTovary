package com.zakrodionov.roskachestvo.domain.interactor.product

import com.zakrodionov.roskachestvo.app.functional.Either
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.domain.entity.Products
import com.zakrodionov.roskachestvo.domain.interactor.UseCase
import com.zakrodionov.roskachestvo.domain.repository.ProductRepository
import javax.inject.Inject

class GetProducts
@Inject constructor(private val productRepository: ProductRepository) : UseCase<List<Products>, UseCase.None>() {

    override suspend fun run(params: None): Either<Failure, List<Products>> = productRepository.getProducts()
}