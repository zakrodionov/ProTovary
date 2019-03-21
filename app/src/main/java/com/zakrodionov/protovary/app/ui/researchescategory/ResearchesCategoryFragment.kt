package com.zakrodionov.protovary.app.ui.researchescategory

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.failure
import com.zakrodionov.protovary.app.ext.observe
import com.zakrodionov.protovary.app.ext.toggleVisibility
import com.zakrodionov.protovary.app.ext.viewModel
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.app.ui.view.ListPaddingDecoration
import com.zakrodionov.protovary.domain.entity.Researches
import kotlinx.android.synthetic.main.toolbar_main.*
import kotlinx.android.synthetic.main.toolbar_search.*
import kotlinx.android.synthetic.main.view_researches_category.*
import javax.inject.Inject

class ResearchesCategoryFragment : BaseFragment() {

    @Inject
    lateinit var researchesCategoryAdapter: ResearchesCategoryAdapter

    override fun layoutId() = R.layout.view_researches_category
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var researchesCategoryViewModel: ResearchesCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        researchesCategoryViewModel = viewModel(viewModelFactory) {
            observe(researches, ::renderResearchesList)
            observe(loading, ::loadingStatus)
            failure(failure, ::handleFailure)
        }

        initializeView()

        if (savedInstanceState.isFirstTimeCreated()) {
            loadResearchList()

        }
    }


    private fun initializeView() {
        tvToolbarTitle.text = getString(R.string.researches)

        rvResearches.addItemDecoration(ListPaddingDecoration(activity!!))
        rvResearches.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvResearches.adapter = researchesCategoryAdapter

        researchesCategoryAdapter.clickListener = ::itemClickListener
    }

    private fun itemClickListener(research: Researches) {
        val bundle = bundleOf("id" to research.id)
        findNavController().navigate(R.id.action_researchesCategoryFragment_to_researchesFragment, bundle)
    }

    private fun loadResearchList() {
        researchesCategoryViewModel.loadResearches()
    }

    private fun renderResearchesList(research: List<Researches>?) {
        researchesCategoryAdapter.collection = research.orEmpty()

        tvEmpty?.toggleVisibility(research.isNullOrEmpty())
        rvResearches?.toggleVisibility(!research.isNullOrEmpty())
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
            is Failure.ServerError -> notify(R.string.failure_unknown_error)
            is Failure.NetworkConnection -> notifyWithAction(
                R.string.failure_network_connection,
                R.string.action_refresh,
                ::loadResearchList
            )
            is Failure.CacheFailure<*> -> notifyWithAction(
                R.string.failure_cache_date, R.string.action_refresh, ::loadResearchList,
                Snackbar.LENGTH_SHORT
            )
        }
    }
}