package com.zakrodionov.roskachestvo.app.di.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zakrodionov.roskachestvo.app.ui.barcode.BarcodeViewModel
import com.zakrodionov.roskachestvo.app.ui.favorites.FavoritesViewModel
import com.zakrodionov.roskachestvo.app.ui.researches.ResearchesViewModel
import com.zakrodionov.roskachestvo.app.ui.researchescategory.ResearchesCategoryViewModel
import com.zakrodionov.roskachestvo.app.ui.search.SearchViewModel
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
    abstract fun bindsResearchesViewModel(researchesCategoryViewModel: ResearchesCategoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindsSearchViewModel(searchViewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ResearchesViewModel::class)
    abstract fun bindsResearchViewModel(researchesViewModel: ResearchesViewModel): ViewModel

}