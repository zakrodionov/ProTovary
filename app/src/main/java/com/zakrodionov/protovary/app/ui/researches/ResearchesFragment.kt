package com.zakrodionov.protovary.app.ui.researches

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.*
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.app.ui.view.ListPaddingDecoration
import com.zakrodionov.protovary.domain.entity.ResearchCompact
import kotlinx.android.synthetic.main.toolbar_search.*
import kotlinx.android.synthetic.main.view_researches.*
import javax.inject.Inject


class ResearchesFragment : BaseFragment() {

    @Inject
    lateinit var researchesAdapter: ResearchesAdapter

    override fun layoutId() = R.layout.view_researches
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var researchesViewModel: ResearchesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        researchesViewModel = viewModel(viewModelFactory) {
            observe(filteredResearches, ::renderResearchesList)
            observe(title, ::renderTitle)
            observe(queryText) { researchesViewModel.applyQueryText() }
            observe(loading, ::loadingStatus)
            failure(failure, ::handleFailure)
        }

        if (savedInstanceState.isFirstTimeCreated()) {
            loadResearches()
        }

        initializeView()
        setupToolbar()
    }


    private fun initializeView() {
        rvResearches.addItemDecoration(ListPaddingDecoration(activity!!))
        rvResearches.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvResearches.adapter = researchesAdapter
    }

    private fun loadResearches() {
        val id = arguments?.getLong("id") ?: 0
        researchesViewModel.loadResearchesCategory(id)
    }

    private fun renderResearchesList(researches: List<ResearchCompact>?) {
        researchesAdapter.collection = researches ?: listOf()
        researchesAdapter.clickListener = ::itemClickListener

        tvEmpty?.toggleVisibility(researches.isNullOrEmpty())
        rvResearches?.toggleVisibility(!researches.isNullOrEmpty())
    }

    private fun itemClickListener(research: ResearchCompact) {
        actionSearch.onActionViewCollapsed()
        val bundle = bundleOf("id" to research.id)
        findNavController().navigate(R.id.action_researchesFragment_to_researchFragment, bundle)
    }

    private fun setupToolbar() {
        actionBack.setOnClickListener { close() }

        val editText = actionSearch.findViewById(R.id.search_src_text) as EditText
        editText.setTextColor(Color.BLACK)
        editText.setHintTextColor(Color.BLACK)

        val searchClose = actionSearch.findViewById(R.id.search_close_btn) as ImageView
        searchClose.setImageResource(R.drawable.ic_close)

        actionSearch.setOnCloseListener {
            tvTitle.visible()
            false
        }

        actionSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                actionSearch.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                researchesViewModel.queryText.value = newText
                return false
            }
        })

        editText.setOnFocusChangeListener { v, hasFocus -> if (hasFocus) tvTitle.gone() }

        tvTitle.text = researchesViewModel.title.value ?: ""
    }

    private fun renderTitle(title: String?) {
        tvTitle?.text = title
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
            is Failure.UnknownError -> notify(R.string.failure_unknown_error)
        }
    }
}