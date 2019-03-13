package com.zakrodionov.roskachestvo.app.ui.more

import android.os.Bundle
import android.view.View
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.viewModel
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import kotlinx.android.synthetic.main.toolbar_main.*

class MoreFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_more
    override fun navigationLayoutId() = R.id.hostFragment
    override fun failureHolderId() = R.id.failureHolder

    private lateinit var moreViewModel: MoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        moreViewModel = viewModel(viewModelFactory) {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
    }

    private fun initializeView() {
        tvToolbarTitle.text = getString(R.string.more)

    }
}