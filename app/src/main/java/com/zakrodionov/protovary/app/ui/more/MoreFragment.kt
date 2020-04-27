package com.zakrodionov.protovary.app.ui.more

import android.os.Bundle
import android.view.View
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.openAppLink
import com.zakrodionov.protovary.app.ext.openPlayMarket
import com.zakrodionov.protovary.app.ext.tryOpenLink
import com.zakrodionov.protovary.app.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_more.*
import kotlinx.android.synthetic.main.toolbar_main.*

class MoreFragment : BaseFragment(R.layout.fragment_more) {

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
        clThemeApp.setOnClickListener { showThemePreferenceDialog() }
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

    private fun showThemePreferenceDialog() {
        navController.navigate(MoreFragmentDirections.actionShowThemePreferenceDialog())
    }

}
