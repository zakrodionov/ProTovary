package com.zakrodionov.roskachestvo.di

import com.zakrodionov.roskachestvo.util.ApplicationSchedulerProvider
import org.koin.dsl.module.module

/**
 * App Components
 */
val appModule = module {
    // Rx Schedulers
    single<ApplicationSchedulerProvider>(createOnStart = true) { ApplicationSchedulerProvider() }
}

// Gather all app modules
val onlineApp = listOf(appModule, localAndroidDataSourceModule, remoteDataSourceModule)
