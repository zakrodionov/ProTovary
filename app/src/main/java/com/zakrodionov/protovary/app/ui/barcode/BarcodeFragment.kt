package com.zakrodionov.protovary.app.ui.barcode

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.viewModel
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.ui.view.DeniedDialogFragment
import com.zakrodionov.protovary.app.ui.view.NeverAskDialogFragment
import kotlinx.android.synthetic.main.toolbar_main.*
import kotlinx.android.synthetic.main.view_barcode.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class BarcodeFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_barcode
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var barcodeViewModel: BarcodeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        barcodeViewModel = viewModel(viewModelFactory) {}

        initializeView()
    }

    private fun initializeView() {
        tvToolbarTitle.text = getString(R.string.barcode)

        actionScan.setOnClickListener { scanBarcodeCustomLayoutWithPermissionCheck() }
        ivActionBarcode.setOnClickListener { scanBarcodeCustomLayoutWithPermissionCheck() }
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun scanBarcodeCustomLayout() {
        navController.navigate(R.id.action_barcodeFragment_to_scannerFragment)
    }

    @OnPermissionDenied(Manifest.permission.CAMERA)
    fun onCameraDenied() {
        DeniedDialogFragment().show(fragmentManager!!, "tag")
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        NeverAskDialogFragment().show(fragmentManager!!, "tag")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }

}