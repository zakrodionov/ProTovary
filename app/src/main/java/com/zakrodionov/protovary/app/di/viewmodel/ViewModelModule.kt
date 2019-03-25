package com.zakrodionov.protovary.app.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zakrodionov.protovary.app.ui.about.AboutViewModel
import com.zakrodionov.protovary.app.ui.barcode.BarcodeViewModel
import com.zakrodionov.protovary.app.ui.favorites.FavoritesViewModel
import com.zakrodionov.protovary.app.ui.more.MoreViewModel
import com.zakrodionov.protovary.app.ui.product.ProductViewModel
import com.zakrodionov.protovary.app.ui.research.ResearchViewModel
import com.zakrodionov.protovary.app.ui.researches.ResearchesViewModel
import com.zakrodionov.protovary.app.ui.researchescategory.ResearchesCategoryViewModel
import com.zakrodionov.protovary.app.ui.scanner.ScannerViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BarcodeViewModel::class)
    abstract fun bindsBarcodeViewModel(barcodeViewModel: BarcodeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindsFavoritesViewModel(favoritesViewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResearchesCategoryViewModel::class)
    abstract fun bindsResearchesCategoryViewModel(researchesCategoryViewModel: ResearchesCategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MoreViewModel::class)
    abstract fun bindsMoreViewModel(moreViewModel: MoreViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResearchesViewModel::class)
    abstract fun bindsResearchesViewModel(researchesViewModel: ResearchesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResearchViewModel::class)
    abstract fun bindsResearchViewModel(researchViewModel: ResearchViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(ProductViewModel::class)
    abstract fun bindsProductViewModel(productViewModel: ProductViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    abstract fun bindsAboutViewModel(aboutViewModel: AboutViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ScannerViewModel::class)
    abstract fun bindsScannerViewModel(scannerViewModel: ScannerViewModel): ViewModel

}