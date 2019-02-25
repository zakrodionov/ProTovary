package com.zakrodionov.roskachestvo.app.ui.barcode

import android.os.Bundle
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.viewModel
import com.zakrodionov.roskachestvo.app.platform.BaseFragment

class BarcodeFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_barcode

    private lateinit var barcodeViewModel: BarcodeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        barcodeViewModel = viewModel(viewModelFactory) {

        }
    }

}