package com.zakrodionov.protovary.app.ui.view

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.zakrodionov.protovary.R

class DeniedDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!, R.style.Dialog_App)

        builder.setMessage(R.string.message_denied_barcode)
            .setPositiveButton(R.string.text_positive) { dialog, _ -> dialog.dismiss() }

        return builder.create()
    }
}
