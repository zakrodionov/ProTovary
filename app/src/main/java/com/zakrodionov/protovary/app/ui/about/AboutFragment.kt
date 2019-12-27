package com.zakrodionov.protovary.app.ui.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.toolbar_back_title.*

class AboutFragment : BaseFragment(R.layout.fragment_about) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
    }

    private fun initializeView() {
        tvText.movementMethod = LinkMovementMethod.getInstance()

        tvTitle.text = getString(R.string.about_app)
        actionBack.setOnClickListener { back() }

        val versionName = BuildConfig.VERSION_NAME
        tvVersion.text = getString(R.string.app_version, versionName)
    }
}