package com.zakrodionov.roskachestvo.app.ui.favorites

import android.os.Bundle
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.viewModel
import com.zakrodionov.roskachestvo.app.platform.BaseFragment

class FavoritesFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_favorites

    private lateinit var favoritesViewModel: FavoritesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        favoritesViewModel = viewModel(viewModelFactory) {

        }
    }
}