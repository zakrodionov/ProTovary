package com.zakrodionov.roskachestvo.domain.interactor.product

import androidx.lifecycle.LiveData
import com.zakrodionov.roskachestvo.domain.repository.ProductRepository
import javax.inject.Inject

class ProductIsFavoriteUseCase
@Inject constructor(private val productRepository: ProductRepository)  {

    fun execute(params: Params): LiveData<Int> = productRepository.productIsFavorite(params.id)

    data class Params(val id: Long)
}