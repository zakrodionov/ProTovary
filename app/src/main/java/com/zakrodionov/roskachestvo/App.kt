package com.zakrodionov.roskachestvo

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.zakrodionov.roskachestvo.di.Properties
import com.zakrodionov.roskachestvo.di.onlineApp
import org.koin.android.ext.android.startKoin
import org.koin.android.logger.AndroidLogger

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Hawk.init(this).build()

        startKoin(
            this,
            onlineApp,
            logger = AndroidLogger(showDebug = true),
            extraProperties = mapOf(Pair(Properties.SERVER_URL, BuildConfig.API_ENDPOINT))
        )
    }
}