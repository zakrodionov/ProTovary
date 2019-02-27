package com.zakrodionov.roskachestvo.app.di

import com.zakrodionov.roskachestvo.app.AndroidApplication
import com.zakrodionov.roskachestvo.app.di.viewmodel.ViewModelModule
import com.zakrodionov.roskachestvo.app.ui.barcode.BarcodeFragment
import com.zakrodionov.roskachestvo.app.ui.favorites.FavoritesFragment
import com.zakrodionov.roskachestvo.app.ui.researches.ResearchesFragment
import com.zakrodionov.roskachestvo.app.ui.researchescategory.ResearchesCategoryFragment
import com.zakrodionov.roskachestvo.app.ui.search.SearchFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: AndroidApplication)

    fun inject(fragment: BarcodeFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: ResearchesCategoryFragment)
    fun inject(fragment: SearchFragment)

    fun inject(fragment: ResearchesFragment)


}
