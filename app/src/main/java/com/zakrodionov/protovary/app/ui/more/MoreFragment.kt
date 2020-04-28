package com.zakrodionov.protovary.app.ui.more

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.openAppLink
import com.zakrodionov.protovary.app.ext.openPlayMarket
import com.zakrodionov.protovary.app.ext.setData
import com.zakrodionov.protovary.app.ext.tryOpenLink
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.platform.DisplayableItem
import com.zakrodionov.protovary.app.ui.more.delegates.MenuItem
import com.zakrodionov.protovary.app.ui.more.delegates.MenuItem.*
import com.zakrodionov.protovary.app.ui.more.delegates.menuDelegate
import kotlinx.android.synthetic.main.fragment_more.*
import kotlinx.android.synthetic.main.toolbar_main.*

class MoreFragment : BaseFragment(R.layout.fragment_more) {

    private val menuAdapter: ListDelegationAdapter<List<DisplayableItem>> by lazy {
        ListDelegationAdapter(menuDelegate(::itemClickListener))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
    }

    private fun initializeView() {
        tvToolbarTitle.text = getString(R.string.more)

        rvMenu.layoutManager = LinearLayoutManager(activity)
        rvMenu.adapter = menuAdapter
        menuAdapter.setData(MenuItem.values().toList())
    }

    private fun itemClickListener(menuItem: MenuItem) {
        when (menuItem) {
            RATE -> openPlayMarket()
            ABOUT -> navigateToAbout()
            TELEGRAM -> openTelegram()
            SEARCH -> tryOpenLink(getString(R.string.url_search))
            THEME -> showThemePreferenceDialog()
        }
    }

    private fun openPlayMarket() {
        activity?.openPlayMarket(
            pmPackage = getString(R.string.pm_package),
            errorMessage = getString(R.string.pm_or_browser_not_installed),
            linkBrowser = getString(R.string.pm_link_browser)
        )
    }

    private fun navigateToAbout() {
        navController.navigate(R.id.action_moreFragment_to_aboutFragment)
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
