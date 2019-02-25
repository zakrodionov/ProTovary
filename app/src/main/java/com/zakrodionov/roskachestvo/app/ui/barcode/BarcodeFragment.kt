package com.zakrodionov.roskachestvo.app.ui.barcode

import android.os.Bundle
import android.view.View
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.viewModel
import com.zakrodionov.roskachestvo.app.platform.BaseFragment

class BarcodeFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_barcode
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var barcodeViewModel: BarcodeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        barcodeViewModel = viewModel(viewModelFactory) {

        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}