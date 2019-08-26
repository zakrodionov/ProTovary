package com.zakrodionov.protovary.app.ui.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.platform.BaseFragment
import kotlinx.android.synthetic.main.toolbar_back_title.*
import kotlinx.android.synthetic.main.view_about.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AboutFragment : BaseFragment(R.layout.view_about) {

    private val aboutViewModel: AboutViewModel by viewModel()

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