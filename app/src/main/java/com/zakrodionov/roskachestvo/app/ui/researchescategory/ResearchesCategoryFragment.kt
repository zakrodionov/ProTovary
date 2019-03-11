package com.zakrodionov.roskachestvo.app.ui.researchescategory

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.*
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.app.ui.view.ListPaddingDecoration
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory
import kotlinx.android.synthetic.main.failure_holder.*
import kotlinx.android.synthetic.main.view_researches_category.*
import javax.inject.Inject

class ResearchesCategoryFragment : BaseFragment() {

    @Inject
    lateinit var researchesCategoryAdapter: ResearchesCategoryAdapter

    override fun layoutId() = R.layout.view_researches_category
    override fun failureHolderId() = R.id.failureHolder
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var researchesCategoryViewModel: ResearchesCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        researchesCategoryViewModel = viewModel(viewModelFactory) {
            observe(researches, ::renderResearchesList)
            observe(loading, ::loadingStatus)
            failure(failure, ::handleFailure)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadResearchList()
    }


    private fun initializeView() {
        rvResearches.addItemDecoration(ListPaddingDecoration(activity!!))
        rvResearches.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvResearches.adapter = researchesCategoryAdapter

        researchesCategoryAdapter.clickListener = ::itemClickListener
    }

    private fun itemClickListener(research: ResearchesCategory) {
        val bundle = Bundle().apply { putLong("id", research.id) }
        navController.navigate(R.id.action_researchesCategoryFragment_to_researchesFragment, bundle)
    }

    private fun loadResearchList() {
        researchesCategoryViewModel.loadResearches()
    }

    private fun renderResearchesList(researches: List<ResearchesCategory>?) {
        failureHolder?.gone()
        researchesCategoryAdapter.collection = researches.orEmpty()
    }

    private fun handleFailure(failure: Failure?) {
        failureHolder?.visible()
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
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