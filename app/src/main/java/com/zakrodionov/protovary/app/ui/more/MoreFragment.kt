package com.zakrodionov.protovary.app.ui.more

import android.os.Bundle
import android.view.View
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.openAppLink
import com.zakrodionov.protovary.app.ext.openPlayMarket
import com.zakrodionov.protovary.app.ext.tryOpenLink
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.util.ThemeHelper
import com.zakrodionov.protovary.data.storage.PreferenceStorage
import kotlinx.android.synthetic.main.fragment_more.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.koin.android.ext.android.inject

class MoreFragment : BaseFragment(R.layout.fragment_more) {

    private val preferenceStorage: PreferenceStorage by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
    }

    //todo переделать под список
    private fun initializeView() {
        tvToolbarTitle.text = getString(R.string.more)

        clActionRate.setOnClickListener { openPlayMarket() }
        clAboutApp.setOnClickListener { navController.navigate(R.id.action_moreFragment_to_aboutFragment) }
        clActionTelegram.setOnClickListener { openTelegram() }
        clActionSearch.setOnClickListener { tryOpenLink(getString(R.string.url_search)) }
        clThemeApp.setOnClickListener { showThemeDialog() }
    }

    private fun openPlayMarket() {
        activity?.openPlayMarket(
            pmPackage = getString(R.string.pm_package),
            errorMessage = getString(R.string.pm_or_browser_not_installed),
            linkBrowser = getString(R.string.pm_link_browser)
        )
    }

    private fun openTelegram() {
        activity?.openAppLink(
            appPackage = getString(R.string.tg_package),
            url = getString(R.string.tg_my_url)
        )
    }

    //TODO переделать диалог
    private fun showThemeDialog() {
        val themeList = resources.getStringArray(R.array.themeListArray)
        val themeEntry = resources.getStringArray(R.array.themeEntryArray)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.preferences_theme)
            .setNegativeButton(R.string.text_negative, null)
            .setSingleChoiceItems(
                themeList,
                ThemeHelper.getThemeIndex(requireContext(), preferenceStorage.theme)
            ) { dialog, which ->
                val theme = themeEntry[which]
                preferenceStorage.theme = theme
                ThemeHelper.applyTheme(theme)
                dialog.dismiss()
            }
            .create()
            .show()
    }

}
