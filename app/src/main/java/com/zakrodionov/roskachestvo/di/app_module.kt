package com.zakrodionov.roskachestvo.di

import com.zakrodionov.roskachestvo.model.ApplicationSchedulerProvider
import com.zakrodionov.roskachestvo.model.repository.MainRepository
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
