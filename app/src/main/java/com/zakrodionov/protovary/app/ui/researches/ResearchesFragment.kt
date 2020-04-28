package com.zakrodionov.protovary.app.ui.researches

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.*
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.platform.DisplayableItem
import com.zakrodionov.protovary.app.ui.researches.delegates.researchDelegate
import com.zakrodionov.protovary.app.util.ColorUtils
import com.zakrodionov.protovary.data.entity.ResearchCompact
import kotlinx.android.synthetic.main.fragment_researches.*
import kotlinx.android.synthetic.main.toolbar_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ResearchesFragment : BaseFragment(R.layout.fragment_researches) {

    private val args: ResearchesFragmentArgs by navArgs()
    private val researchesId by lazy { args.researchId }
    private val researchesViewModel: ResearchesViewModel by viewModel { parametersOf(researchesId) }
    private val researchesAdapter: ListDelegationAdapter<List<DisplayableItem>> by lazy {
        ListDelegationAdapter(researchDelegate(::itemClickListener))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(researchesViewModel) {
            observe(filteredResearches, ::renderResearchesList)
            observe(title, ::renderTitle)
            observeEvent(state, ::handleState)
        }

        initializeView()
        setupToolbar()
    }

    private fun initializeView() {
        rvResearches.layoutManager = LinearLayoutManager(activity)
        rvResearches.adapter = researchesAdapter
    }

    private fun renderResearchesList(researches: List<ResearchCompact>?) {
        researchesAdapter.setData(researches)

        tvEmpty?.isVisible = researches.isNullOrEmpty()
        rvResearches?.isVisible = !researches.isNullOrEmpty()
    }

    private fun itemClickListener(research: ResearchCompact) {
        closeSearch()
        val directions =
            ResearchesFragmentDirections.actionResearchesFragmentToResearchFragment(research.id)
        navController.navigate(directions)
    }

    private fun setupToolbar() {
        actionBack.setOnClickListener { back() }

        val editText = actionSearch.findViewById(R.id.search_src_text) as EditText
        editText.setTextColor(ColorUtils.getThemeColor(requireContext(), R.attr.textColor))
        editText.setHintTextColor(ColorUtils.getThemeColor(requireContext(), R.attr.textColor))

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
                researchesViewModel.queryText = newText
                rvResearches?.scrollToPosition(0)
                return false
            }
        })

        editText.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus -> if (hasFocus) tvTitle.gone() }
    }

    private fun closeSearch() {
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

    override fun loadData() = researchesViewModel.loadResearchesCategory(researchesId)
}
