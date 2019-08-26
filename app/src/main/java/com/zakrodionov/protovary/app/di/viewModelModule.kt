package com.zakrodionov.protovary.app.di

import com.zakrodionov.protovary.app.ui.about.AboutViewModel
import com.zakrodionov.protovary.app.ui.barcode.BarcodeViewModel
import com.zakrodionov.protovary.app.ui.favorites.FavoritesViewModel
import com.zakrodionov.protovary.app.ui.more.MoreViewModel
import com.zakrodionov.protovary.app.ui.product.ProductViewModel
import com.zakrodionov.protovary.app.ui.research.ResearchViewModel
import com.zakrodionov.protovary.app.ui.researches.ResearchesViewModel
import com.zakrodionov.protovary.app.ui.researchescategory.ResearchesCategoryViewModel
import com.zakrodionov.protovary.app.ui.scanner.ScannerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { AboutViewModel() }
    viewModel { BarcodeViewModel() }
    viewModel { FavoritesViewModel(get()) }
    viewModel { MoreViewModel() }
    viewModel { (id : Long) -> ProductViewModel(id, get(), get(), get()) }
    viewModel { (id : Long) -> ResearchViewModel(id, get(), get(), get()) }
    viewModel { (id : Long) -> ResearchesViewModel(id, get()) }
    viewModel { ResearchesCategoryViewModel(get()) }
    viewModel { ScannerViewModel(get()) }

}