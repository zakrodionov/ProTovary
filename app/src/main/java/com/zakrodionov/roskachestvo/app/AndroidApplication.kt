package com.zakrodionov.roskachestvo.app

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.zakrodionov.roskachestvo.app.di.ApplicationComponent
import com.zakrodionov.roskachestvo.app.di.ApplicationModule
import com.zakrodionov.roskachestvo.app.di.DaggerApplicationComponent


class AndroidApplication : Application() {

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
    }

    private fun injectMembers() = appComponent.inject(this)

}
