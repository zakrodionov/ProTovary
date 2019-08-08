package com.zakrodionov.protovary.app

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.app.di.appModule
import com.zakrodionov.protovary.app.di.viewModelModule
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()

        if (BuildConfig.DEBUG) {
            initDebugComponents()
        } else {
            initProductionComponents()
        }
    }

    private fun initDebugComponents() {
        Stetho.initializeWithDefaults(this)
    }

    private fun initProductionComponents() {
        Fabric.with(this, Crashlytics())
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            androidLogger()
            modules(listOf(appModule, viewModelModule))
        }
    }
}
