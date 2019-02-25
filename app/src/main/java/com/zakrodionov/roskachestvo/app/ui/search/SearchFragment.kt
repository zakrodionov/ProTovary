package com.zakrodionov.roskachestvo.app.ui.search

import android.os.Bundle
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.viewModel
import com.zakrodionov.roskachestvo.app.platform.BaseFragment

class SearchFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_search
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        searchViewModel = viewModel(viewModelFactory) {

        }
    }
}