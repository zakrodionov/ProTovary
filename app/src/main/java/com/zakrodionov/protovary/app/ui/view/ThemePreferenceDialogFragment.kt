package com.zakrodionov.protovary.app.ui.view

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.util.ThemeUtils
import com.zakrodionov.protovary.data.storage.PreferenceStorage
import org.koin.android.ext.android.inject

class ThemePreferenceDialogFragment : DialogFragment() {

    private val preferenceStorage: PreferenceStorage by inject()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())

        val themeTitles = resources.getStringArray(R.array.themeTitles)
        val themeValues = resources.getStringArray(R.array.themeValues)

        builder.apply {
            setTitle(R.string.preferences_theme)
            setNegativeButton(R.string.text_negative, null)
            setSingleChoiceItems(
                themeTitles,
                ThemeUtils.getThemeIndex(requireContext(), preferenceStorage.theme)
            ) { dialog, which ->
                val theme = themeValues[which]
                preferenceStorage.theme = theme
                ThemeUtils.applyTheme(theme)
                dialog.dismiss()
            }
        }

        return builder.create()
    }

}
