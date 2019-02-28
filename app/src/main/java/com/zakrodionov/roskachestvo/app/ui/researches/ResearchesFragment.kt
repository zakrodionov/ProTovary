package com.zakrodionov.roskachestvo.app.ui.researches

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.*
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.app.ui.view.ListPaddingDecoration
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory
import kotlinx.android.synthetic.main.failure_holder.*
import kotlinx.android.synthetic.main.toolbar_search.*
import kotlinx.android.synthetic.main.view_researches.*
import javax.inject.Inject
import android.widget.ImageView


class ResearchesFragment : BaseFragment() {

    @Inject
    lateinit var researchesAdapter: ResearchesAdapter

    override fun layoutId() = R.layout.view_researches
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
        setupToolbar()
    }


    private fun initializeView() {
        rvResearches.addItemDecoration(ListPaddingDecoration(activity!!))
        rvResearches.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvResearches.adapter = researchesAdapter
    }

    private fun setResearches() {
        val id = arguments?.getLong("id") ?: 0
        researchesViewModel.loadResearchesCategory(id)
    }

    private fun renderResearchesList(researchesCategory: ResearchesCategory?) {
        researchesAdapter.collection = researchesCategory?.researches ?: listOf()
        failureHolder?.gone()
    }

    private fun setupToolbar() {

        actionBack.setOnClickListener { navController.popBackStack() }

        val editText = actionSearch.findViewById(R.id.search_src_text) as EditText
        editText.setTextColor(Color.WHITE)

        val searchClose = actionSearch.findViewById(R.id.search_close_btn) as ImageView
        searchClose.setImageResource(R.drawable.ic_close_white)

        actionSearch.setOnCloseListener {
            tvTitle.visible()
             false
        }

        actionSearch.setOnSearchClickListener {
            tvTitle.gone()
        }

        actionSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                actionSearch.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
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