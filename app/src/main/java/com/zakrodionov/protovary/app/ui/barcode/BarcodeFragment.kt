package com.zakrodionov.protovary.app.ui.barcode

import android.Manifest
import android.os.Bundle
import android.view.View
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.ui.view.DeniedDialogFragment
import com.zakrodionov.protovary.app.ui.view.NeverAskDialogFragment
import kotlinx.android.synthetic.main.fragment_barcode.*
import kotlinx.android.synthetic.main.toolbar_main.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class BarcodeFragment : BaseFragment(R.layout.fragment_barcode) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        DeniedDialogFragment().show(fragmentManager!!, PERMISSION_DIALOG_TAG)
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraNeverAskAgain() {
        NeverAskDialogFragment().show(fragmentManager!!, PERMISSION_DIALOG_TAG)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    companion object {
        const val PERMISSION_DIALOG_TAG = "permission_dialog_tag"
    }
}