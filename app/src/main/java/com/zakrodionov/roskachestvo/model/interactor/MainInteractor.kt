package com.zakrodionov.roskachestvo.model.interactor

import com.zakrodionov.roskachestvo.model.ApplicationSchedulerProvider
import com.zakrodionov.roskachestvo.model.repository.MainRepository

class MainInteractor(
    private val mainRepository: MainRepository,
    private val schedulerProvider: ApplicationSchedulerProvider
) {

    fun getProducts() = mainRepository.getProducts()

    fun getResearches() = mainRepository.getResearches()

}