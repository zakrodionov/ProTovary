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
import com.zakrodionov.protovary.app.ui.view.ListPaddingDecoration
import com.zakrodionov.protovary.data.entity.ResearchCompact
import kotlinx.android.synthetic.main.toolbar_search.actionBack
import kotlinx.android.synthetic.main.toolbar_search.actionSearch
import kotlinx.android.synthetic.main.toolbar_search.tvTitle
import kotlinx.android.synthetic.main.view_researches.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ResearchesFragment : BaseFragment(R.layout.view_researches) {

    private val researchesId: Long by argument("id", 0L)
    private val researchesViewModel: ResearchesViewModel by viewModel{ parametersOf(researchesId) }
    private val researchesAdapter: ResearchesAdapter by lazy { ResearchesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(researchesViewModel) {
            observe(filteredResearches, ::renderResearchesList)
            observe(title, ::renderTitle)
            observe(queryText) { researchesViewModel.applyQueryText() }
            observe(state, ::handleState)
        }

        initializeView()
        setupToolbar()
    }


    private fun initializeView() {
        rvResearches.addItemDecoration(ListPaddingDecoration(activity!!))
        rvResearches.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvResearches.adapter = researchesAdapter
    }

    private fun renderResearchesList(researches: List<ResearchCompact>?) {
        researchesAdapter.collection = researches ?: listOf()
        researchesAdapter.clickListener = ::itemClickListener

        tvEmpty?.toggleVisibility(researches.isNullOrEmpty())
        rvResearches?.toggleVisibility(!researches.isNullOrEmpty())
    }

    private fun itemClickListener(research: ResearchCompact) {
        closeSearch()
        val bundle = bundleOf("id" to research.id)
        navController.navigate(R.id.action_researchesFragment_to_researchFragment, bundle)
    }

    private fun setupToolbar() {
        actionBack.setOnClickListener { back() }

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
                if (newText.isNotEmpty() || !actionSearch.isIconified) {
                    tvTitle.gone()
                }
                researchesViewModel.queryText.value = newText
                rvResearches?.scrollToPosition(0)
                return false
            }
        })

        editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus -> if (hasFocus) tvTitle.gone() }
    }

    private fun closeSearch(){
        if (actionSearch.query.isNullOrEmpty()) {
            actionSearch.onActionViewCollapsed()
        }
    }

    private fun renderTitle(title: String?) {
        tvTitle?.text = title
    }

    override fun onDestroyView() {
        rvResearches.adapter = null
        super.onDestroyView()
    }

    override fun loadData() =  researchesViewModel.loadResearchesCategory(researchesId)
}