package com.zakrodionov.protovary.app.ui.view

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class SimpleScannerFragment : Fragment(), ZBarScannerView.ResultHandler {

    private var scannerView: ZBarScannerView? = null
    var resultListener: ((Result?) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        scannerView = ZBarScannerView(activity)
        return scannerView
    }

    override fun onResume() {
        super.onResume()
        scannerView?.setResultHandler(this)
        scannerView?.startCamera()
    }

    override fun handleResult(rawResult: Result?) {
        resultListener?.invoke(rawResult)
        val handler = Handler()
        handler.postDelayed({ scannerView?.resumeCameraPreview(this) }, 2000)
    }

    override fun onPause() {
        super.onPause()
        scannerView?.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        resultListener = null
    }
}
