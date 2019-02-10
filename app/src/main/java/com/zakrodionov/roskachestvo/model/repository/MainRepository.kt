package com.zakrodionov.roskachestvo.model.repository

import com.zakrodionov.roskachestvo.model.ApplicationSchedulerProvider
import com.zakrodionov.roskachestvo.model.data.server.Api

class MainRepository(private val api: Api, private val schedulerProvider: ApplicationSchedulerProvider) {
    fun getProducts() = api.getProducts()
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())

    fun getResearches() = api.getResearches()
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
}