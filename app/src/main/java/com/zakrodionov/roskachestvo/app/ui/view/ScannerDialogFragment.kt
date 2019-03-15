package com.zakrodionov.roskachestvo.app.ui.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.zakrodionov.roskachestvo.R

class ScannerDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(activity!!)
        dialog.setContentView(R.layout.dialog_scanner)



        return dialog
    }
    interface ScannerDialogListener{
        fun actionSearch(text: String)
        fun actionSearchOnSite()
        fun actionScan()
    }
}