package com.zakrodionov.roskachestvo.domain.repository

import com.zakrodionov.roskachestvo.data.network.Api

class MainRepository(private val api: Api, private val schedulerProvider: ApplicationSchedulerProvider) {
    fun getProducts() = api.getProducts()
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())

    fun getResearches() = api.getResearches()
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
}