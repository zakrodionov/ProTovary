package com.zakrodionov.protovary.app

import android.app.Application
import com.facebook.stetho.Stetho
import com.orhanobut.hawk.Hawk
import com.zakrodionov.protovary.app.di.ApplicationComponent
import com.zakrodionov.protovary.app.di.ApplicationModule
import com.zakrodionov.protovary.app.di.DaggerApplicationComponent


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

        Hawk.init(this).build()
        Stetho.initializeWithDefaults(this)

    }

    private fun injectMembers() = appComponent.inject(this)

}
