package com.zakrodionov.protovary.app.ui.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.tryOpenLink
import com.zakrodionov.protovary.app.platform.BaseFragment
import kotlinx.android.synthetic.main.toolbar_main.*
import kotlinx.android.synthetic.main.view_more.*
import org.jetbrains.anko.toast


class MoreFragment : BaseFragment(R.layout.view_more) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
    }

    private fun initializeView() {
        tvToolbarTitle.text = getString(R.string.more)

        clActionRate.setOnClickListener { openPlayMarket() }
        clAboutApp.setOnClickListener { navController.navigate(R.id.action_moreFragment_to_aboutFragment) }
        clActionTelegram.setOnClickListener { openTelegram() }
        clActionSearch.setOnClickListener { tryOpenLink(getString(R.string.url_search)) }

    }

    private fun openPlayMarket() {
        val uri = Uri.parse(getString(R.string.pm_package) + BuildConfig.APPLICATION_ID)
        var intent = Intent(Intent.ACTION_VIEW, uri)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )

        if (intent.resolveActivity(activity!!.packageManager) != null) {
            startActivity(intent)
        } else {
            intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(getString(R.string.pm_link_browser) + BuildConfig.APPLICATION_ID)
            )
            if (intent.resolveActivity(activity!!.packageManager) != null) {
                startActivity(intent)
            } else {
                activity!!.toast(getString(R.string.pm_or_browser_not_installed))
            }
        }
    }

    private fun openTelegram() {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.tg_my_url)))
            intent.setPackage(getString(R.string.tg_package))
            startActivity(intent)
        } catch (e: Exception) {
            tryOpenLink(getString(R.string.tg_my_url))
        }
    }

}