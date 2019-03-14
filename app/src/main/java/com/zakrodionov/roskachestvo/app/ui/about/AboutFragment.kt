package com.zakrodionov.roskachestvo.app.ui.about

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import com.zakrodionov.roskachestvo.BuildConfig
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.viewModel
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import kotlinx.android.synthetic.main.toolbar_back_title.*
import kotlinx.android.synthetic.main.view_about.*

class AboutFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_about
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var aboutViewModel: AboutViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        aboutViewModel = viewModel(viewModelFactory) {

        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }

    private fun initializeView() {
        tvText.movementMethod = LinkMovementMethod.getInstance()

        tvTitle.text = getString(R.string.about_app)
        actionBack.setOnClickListener { navController.popBackStack() }

        val versionName = BuildConfig.VERSION_NAME
        tvVersion.text = "Версия: $versionName"
    }
}