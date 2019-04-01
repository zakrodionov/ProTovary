package com.zakrodionov.protovary.app

import android.app.Application
import com.crashlytics.android.Crashlytics
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.app.di.ApplicationComponent
import com.zakrodionov.protovary.app.di.ApplicationModule
import com.zakrodionov.protovary.app.di.DaggerApplicationComponent
import io.fabric.sdk.android.Fabric


class App : Application() {

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        this.injectMembers()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
            LeakCanary.install(this)
        } else {
            Fabric.with(this, Crashlytics())
        }
    }

    private fun injectMembers() = appComponent.inject(this)

}
