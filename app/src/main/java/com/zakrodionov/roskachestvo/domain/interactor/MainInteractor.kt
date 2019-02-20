package com.zakrodionov.roskachestvo.domain.interactor

import com.zakrodionov.roskachestvo.domain.repository.MainRepository

class MainInteractor(
    private val mainRepository: MainRepository,
    private val schedulerProvider: ApplicationSchedulerProvider
) {

    fun getProducts() = mainRepository.getProducts()

    fun getResearches() = mainRepository.getResearches()

}