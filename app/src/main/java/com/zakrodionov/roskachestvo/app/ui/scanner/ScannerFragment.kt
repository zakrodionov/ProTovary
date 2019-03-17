package com.zakrodionov.roskachestvo.app.ui.scanner

import android.os.Bundle
import android.view.View
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.failure
import com.zakrodionov.roskachestvo.app.ext.observe
import com.zakrodionov.roskachestvo.app.ext.tryOpenLink
import com.zakrodionov.roskachestvo.app.ext.viewModel
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.app.ui.view.ScannerDialogFragment
import com.zakrodionov.roskachestvo.app.ui.view.SimpleScannerFragment
import com.zakrodionov.roskachestvo.domain.entity.ProductCompact
import kotlinx.android.synthetic.main.toolbar_back_title.*


class ScannerFragment : BaseFragment(), ScannerDialogFragment.ScannerDialogListener {

    override fun layoutId() = R.layout.view_scanner
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var scannerViewModel: ScannerViewModel

    var scannerFragment: SimpleScannerFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        scannerViewModel = viewModel(viewModelFactory) {
            observe(product, ::handleProduct)
            observe(loading, ::loadingStatus)
            failure(failure, ::handleFailure)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scannerFragment = childFragmentManager.findFragmentById(R.id.scanner_fragment) as SimpleScannerFragment

        scannerFragment!!.resultListener = {
            if (it?.contents != null) {
                scannerViewModel.loadProduct(it.contents)
            } else {
                showDialog()
            }
        }

        initializeView()

    }

    private fun handleProduct(product: ProductCompact?) {
        if (product?.id != null) {
            val bundle = Bundle().apply { putLong("id", product.id) }
            navController.navigate(R.id.action_scannerFragment_to_productFragment, bundle)
        } else {
            showDialog()
        }
    }

    private fun initializeView() {
        tvTitle.text = getString(R.string.barcode)

        actionBack.setOnClickListener { navController.popBackStack() }
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
            is Failure.NetworkConnection -> notify(R.string.failure_network_connection)
            is Failure.UnknownError -> {
                showDialog()
            }

        }
    }

    private fun showDialog() {
        scannerFragment?.onPause()

        val dialog = ScannerDialogFragment()
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
        scannerFragment?.onResume()
    }

    companion object {
        const val RC_DIALOG = 33226
    }
}