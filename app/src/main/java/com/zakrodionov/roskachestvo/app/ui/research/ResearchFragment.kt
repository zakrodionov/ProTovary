package com.zakrodionov.roskachestvo.app.ui.research

import android.os.Bundle
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.viewModel
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import com.zakrodionov.roskachestvo.app.ui.barcode.BarcodeViewModel

class ResearchFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_research

    private lateinit var researchViewModel: ResearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        researchViewModel = viewModel(viewModelFactory) {

        }
    }
}