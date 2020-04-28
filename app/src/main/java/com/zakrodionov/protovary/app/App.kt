package com.zakrodionov.protovary.app

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.app.di.appModule
import com.zakrodionov.protovary.app.di.viewModelModule
import com.zakrodionov.protovary.app.util.ThemeUtils
import com.zakrodionov.protovary.data.storage.PreferenceStorage
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinComponent
import org.koin.core.context.startKoin
import org.koin.core.get

class App : Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()

        initKoin()

        if (BuildConfig.DEBUG) {
            initDebugComponents()
        } else {
            initProductionComponents()
        }

        setTheme()
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

    private fun setTheme() {
        ThemeUtils.applyTheme(get<PreferenceStorage>().theme)
    }
}
