package com.zakrodionov.protovary.app.ui.scanner

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.observe
import com.zakrodionov.protovary.app.ext.tryOpenLink
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.app.ui.view.ScannerDialogFragment
import com.zakrodionov.protovary.app.ui.view.SimpleScannerFragment
import com.zakrodionov.protovary.data.entity.ProductCompact
import kotlinx.android.synthetic.main.toolbar_back_title.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class ScannerFragment : BaseFragment(R.layout.view_scanner), ScannerDialogFragment.ScannerDialogListener {

    private val scannerViewModel: ScannerViewModel by viewModel()
    private var simpleScanner: SimpleScannerFragment? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(scannerViewModel) {
            observe(product, ::handleProduct)
            observe(state, ::handleState)
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
            navController.navigate(R.id.action_scannerFragment_to_productFragment, bundle)
        } else {
            showDialog()
        }
    }

    private fun initializeView() {
        tvTitle.text = getString(R.string.barcode)

        actionBack.setOnClickListener { back() }
    }

    override fun handleFailure(failure: Failure?) {
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
        dialog.isCancelable = true

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

    override fun actionback() {
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