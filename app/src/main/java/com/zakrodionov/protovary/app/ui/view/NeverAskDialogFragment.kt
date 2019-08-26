package com.zakrodionov.protovary.app.ui.view

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.zakrodionov.protovary.R

class NeverAskDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity!!, R.style.Dialog_App)

        builder.setMessage(R.string.message_ask_never_barcode)
            .setPositiveButton(R.string.text_positive) { dialog, _ -> openSettings(); dialog.dismiss() }
            .setNegativeButton(R.string.text_negative) { dialog, _ -> dialog.dismiss() }

        return builder.create()
    }


    private fun openSettings() {
        val appSettingsIntent =
            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:${activity!!.packageName}"))
        startActivityForResult(appSettingsIntent, 0)
    }
}