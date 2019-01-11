package com.zakrodionov.roskachestvo.di

import com.zakrodionov.roskachestvo.util.LocalCiceroneHolder
import org.koin.dsl.module.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

val navigationModule = module {
    single { LocalCiceroneHolder() }
    single { Cicerone.create() }
    single { (get() as Cicerone<Router>).router }
    single { (get() as Cicerone<Router>).navigatorHolder }
}