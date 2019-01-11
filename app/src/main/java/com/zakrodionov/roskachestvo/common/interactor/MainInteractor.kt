package com.zakrodionov.roskachestvo.common.interactor

import com.zakrodionov.roskachestvo.server.Api
import com.zakrodionov.roskachestvo.util.ApplicationSchedulerProvider

class MainInteractor(private val api: Api, private val schedulerProvider: ApplicationSchedulerProvider) {

    fun getProducts() = api.getProducts()
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())

    fun getResearches() = api.getResearches()
        .subscribeOn(schedulerProvider.io())
        .observeOn(schedulerProvider.ui())
}