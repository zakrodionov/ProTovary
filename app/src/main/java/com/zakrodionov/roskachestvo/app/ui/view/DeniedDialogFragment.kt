package com.zakrodionov.roskachestvo.app.ui.view

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.zakrodionov.roskachestvo.R

class DeniedDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!, R.style.CustomDialogStyle)

        builder.setMessage(R.string.message_denied_barcode)
            .setPositiveButton(R.string.text_positive) { dialog, _ -> dialog.dismiss() }

        return builder.create()
    }
}