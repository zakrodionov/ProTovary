package com.zakrodionov.roskachestvo.app.ui.research

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.failure
import com.zakrodionov.roskachestvo.app.ext.observe
import com.zakrodionov.roskachestvo.app.ext.viewModel
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.app.ui.view.ListPaddingDecoration
import com.zakrodionov.roskachestvo.domain.entity.Researches
import kotlinx.android.synthetic.main.view_research.*
import javax.inject.Inject

class ResearchFragment : BaseFragment() {

    @Inject
    lateinit var researchesAdapter: ResearchesAdapter

    override fun layoutId() = R.layout.view_research
    override fun snackHolderContainer() = R.id.clSnackHolder
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var researchViewModel: ResearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        researchViewModel = viewModel(viewModelFactory) {
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
        rvResearches.adapter = researchesAdapter
    }

    private fun loadResearchList() {
        researchViewModel.loadResearches()
    }

    private fun renderResearchesList(researches: List<Researches>?) {
        researchesAdapter.collection = researches.orEmpty()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
            is Failure.NetworkConnection -> notifyWithAction(R.string.failure_network_connection, R.string.action_refresh, ::loadResearchList)
            is Failure.CacheFailure<*> ->  notifyWithAction(R.string.failure_cache_date, R.string.action_refresh, ::loadResearchList)
        }
    }
}