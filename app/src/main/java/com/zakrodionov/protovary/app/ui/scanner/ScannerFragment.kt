package com.zakrodionov.protovary.app.ui.scanner

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.failure
import com.zakrodionov.protovary.app.ext.observe
import com.zakrodionov.protovary.app.ext.tryOpenLink
import com.zakrodionov.protovary.app.ext.viewModel
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.app.ui.view.ScannerDialogFragment
import com.zakrodionov.protovary.app.ui.view.SimpleScannerFragment
import com.zakrodionov.protovary.data.entity.ProductCompact
import kotlinx.android.synthetic.main.toolbar_back_title.*


class ScannerFragment : BaseFragment(), ScannerDialogFragment.ScannerDialogListener {

    override fun layoutId() = R.layout.view_scanner

    private lateinit var scannerViewModel: ScannerViewModel

    var simpleScanner: SimpleScannerFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        scannerViewModel = viewModel(viewModelFactory) {}
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(scannerViewModel) {
            observe(product, ::handleProduct)
            observe(loading, ::loadingStatus)
            failure(failure, ::handleFailure)
        }

        setupScanner()
        initializeView()
    }

    private fun setupScanner() {
        simpleScanner = childFragmentManager.findFragmentById(R.id.scanner_fragment) as SimpleScannerFragment

        simpleScanner!!.resultListener = {
            if (it?.contents != null) {
                scannerViewModel.loadProduct(it.contents)
            } else {
                showDialog()
            }
        }
    }

    private fun handleProduct(product: ProductCompact?) {
        if (product?.id != null) {
            val bundle = bundleOf("id" to product.id)
            findNavController().navigate(R.id.action_scannerFragment_to_productFragment, bundle)
        } else {
            showDialog()
        }
    }

    private fun initializeView() {
        tvTitle.text = getString(R.string.barcode)

        actionBack.setOnClickListener { close() }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
            is Failure.NetworkConnection -> notify(R.string.failure_network_connection)
            is Failure.UnknownError -> notify(R.string.failure_unknown_error)
            is Failure.BarcodeFailure -> {
                showDialog(failure.barcode)
            }
        }
    }

    private fun showDialog(barcode: String = "") {
        simpleScanner?.onPause()

        val dialog = ScannerDialogFragment()
        val bundle = bundleOf("barcode" to barcode)

        dialog.arguments = bundle
        dialog.setTargetFragment(this, RC_DIALOG)
        dialog.show(fragmentManager!!, "tag")

    }

    override fun actionSearch(text: String) {
        scannerViewModel.loadProduct(text)
    }

    override fun actionSearchOnSite() {
        tryOpenLink(getString(R.string.url_search))
    }

    override fun actionClose() {
        simpleScanner?.onResume()
    }

    override fun onDestroyView() {
        simpleScanner = null
        super.onDestroyView()
    }
    companion object {
        const val RC_DIALOG = 33226
    }
}