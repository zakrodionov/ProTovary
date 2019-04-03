package com.zakrodionov.protovary.app.di

import com.zakrodionov.protovary.app.App
import com.zakrodionov.protovary.app.di.viewmodel.ViewModelModule
import com.zakrodionov.protovary.app.ui.about.AboutFragment
import com.zakrodionov.protovary.app.ui.barcode.BarcodeFragment
import com.zakrodionov.protovary.app.ui.favorites.FavoritesFragment
import com.zakrodionov.protovary.app.ui.more.MoreFragment
import com.zakrodionov.protovary.app.ui.product.ProductFragment
import com.zakrodionov.protovary.app.ui.research.ResearchFragment
import com.zakrodionov.protovary.app.ui.researches.ResearchesFragment
import com.zakrodionov.protovary.app.ui.researchescategory.ResearchesCategoryFragment
import com.zakrodionov.protovary.app.ui.scanner.ScannerFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(application: App)

    fun inject(fragment: BarcodeFragment)
    fun inject(fragment: FavoritesFragment)
    fun inject(fragment: ResearchesCategoryFragment)
    fun inject(fragment: MoreFragment)

    fun inject(fragment: ResearchesFragment)
    fun inject(fragment: ResearchFragment)
    fun inject(fragment: ProductFragment)
    fun inject(fragment: AboutFragment)
    fun inject(fragment: ScannerFragment)


}
