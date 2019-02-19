package com.zakrodionov.roskachestvo.app.di

import com.zakrodionov.roskachestvo.data.network.ApplicationSchedulerProvider
import com.zakrodionov.roskachestvo.domain.repository.MainRepository
import org.koin.dsl.module.module

/**
 * App Components
 */
val appModule = module {
    // Rx Schedulers
    single(createOnStart = true) { ApplicationSchedulerProvider() }

    single { MainRepository(get(), get()) }
}

// Gather all app modules
val onlineApp = listOf(appModule, localAndroidDataSourceModule, remoteDataSourceModule, navigationModule)
