package com.zakrodionov.protovary.app.ui.view

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.util.Utils
import kotlinx.android.synthetic.main.dialog_scanner.*
import kotlinx.android.synthetic.main.dialog_scanner.view.*

class ScannerDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_BARCODE = "arg_barcode"

        fun newInstance(barcode: String) = ScannerDialogFragment().apply {
            arguments = bundleOf(
                ARG_BARCODE to barcode
            )
        }
    }

    var listener: ScannerDialogListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_scanner, container, false)
        setupView(view)

        return view
    }

    private fun setupView(view: View) {
        val barcode = arguments?.getString(ARG_BARCODE) ?: ""

        if (barcode.isEmpty()) {
            view.tvDescription.text = getString(R.string.product_not_found)
        } else {
            val text =
                String.format(getString(R.string.product_not_found_barcode), Utils.formatBarcode(barcode)).parseAsHtml()
            view.tvDescription.text = "$text ${getString(R.string.product_not_found_input_barcode)}"
        }

        listener = targetFragment as? ScannerDialogListener

        view.actionSearch.isEnabled = false

        view.etBarcode.doAfterTextChanged {
            view.actionSearch.isEnabled = it?.length in 8..13
        }

        view.actionSearch.setOnClickListener {
            listener?.actionSearch(etBarcode.text.toString())
            dismiss()
        }

        view.actionSearchOnSite.setOnClickListener {
            listener?.actionSearchOnSite()
            dismiss()
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        listener?.actionBack()
    }

    interface ScannerDialogListener {
        fun actionSearch(text: String)
        fun actionSearchOnSite()
        fun actionBack()
    }
}
