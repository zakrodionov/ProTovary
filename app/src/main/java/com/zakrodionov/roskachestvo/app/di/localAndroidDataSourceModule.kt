package com.zakrodionov.roskachestvo.app.di

import com.zakrodionov.roskachestvo.domain.interactor.SharedPreferenceInteractor
import org.koin.dsl.module.module

val localAndroidDataSourceModule = module {
    single { SharedPreferenceInteractor() }
}