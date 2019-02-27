package com.zakrodionov.roskachestvo.app.ui.researches

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.*
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.app.ui.view.ListPaddingDecoration
import com.zakrodionov.roskachestvo.data.model.ResearchesFragmentModel
import com.zakrodionov.roskachestvo.domain.entity.ResearchCompact
import kotlinx.android.synthetic.main.failure_holder.*
import kotlinx.android.synthetic.main.view_research.*
import javax.inject.Inject

class ResearchesFragment : BaseFragment() {

    @Inject
    lateinit var researchesAdapter: ResearchesAdapter

    override fun layoutId() = R.layout.view_research
    override fun failureHolderId() = R.id.failureHolder
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var researchesViewModel: ResearchesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        researchesViewModel = viewModel(viewModelFactory) {
            observe(researches, ::renderResearchesList)
            observe(loading, ::loadingStatus)
            failure(failure, ::handleFailure)
        }

        setResearches()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
    }


    private fun initializeView() {
        rvResearches.addItemDecoration(ListPaddingDecoration(activity!!))
        rvResearches.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvResearches.adapter = researchesAdapter
    }

    private fun setResearches() {
        val researches = (arguments?.getSerializable("model") as ResearchesFragmentModel).researches ?: listOf()
        researchesViewModel.setResearches(researches)
    }

    private fun renderResearchesList(researches: List<ResearchCompact>?) {
        failureHolder?.gone()
        Log.d("researches", researches.toString())
    }

    private fun handleFailure(failure: Failure?) {
        failureHolder?.visible()
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
            // is Failure.NetworkConnection -> notifyWithAction(R.string.failure_network_connection, R.string.action_refresh, { loadResearch(researchId) })
            //is Failure.CacheFailure<*> ->  notifyWithAction(R.string.failure_cache_date, R.string.action_refresh, { loadResearch(researchId) }, Snackbar.LENGTH_SHORT)
        }
    }
}