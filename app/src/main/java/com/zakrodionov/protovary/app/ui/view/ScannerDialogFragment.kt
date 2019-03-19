package com.zakrodionov.protovary.app.ui.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.afterTextChanged
import kotlinx.android.synthetic.main.dialog_scanner.*
import kotlinx.android.synthetic.main.dialog_scanner.view.*

class ScannerDialogFragment : DialogFragment() {

    var listener: ScannerDialogListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_scanner, container, false)

        listener = targetFragment as? ScannerDialogListener

        view.actionSearch.isEnabled = false

        view.etBarcode.afterTextChanged {
            view.actionSearch.isEnabled = it.length in 8..13
        }

        view.actionSearch.setOnClickListener {
            listener?.actionSearch(etBarcode.text.toString())
            dismiss()
        }

        view.actionSearchOnSite.setOnClickListener {
            listener?.actionSearchOnSite()
            dismiss()
        }

        return view
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        listener?.actionClose()
    }

    interface ScannerDialogListener {
        fun actionSearch(text: String)
        fun actionSearchOnSite()
        fun actionClose()
    }
}