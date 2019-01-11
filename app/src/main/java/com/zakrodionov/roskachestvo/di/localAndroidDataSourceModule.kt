package com.zakrodionov.roskachestvo.di

import com.zakrodionov.roskachestvo.common.interactor.SharedPreferenceInteractor
import org.koin.dsl.module.module

val localAndroidDataSourceModule = module {
    single { SharedPreferenceInteractor() }
}