package com.zakrodionov.protovary.app.ui.researchescategory

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.observe
import com.zakrodionov.protovary.app.ext.observeEvent
import com.zakrodionov.protovary.app.ext.setData
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.platform.DisplayableItem
import com.zakrodionov.protovary.app.ui.researchescategory.delegates.researchCategoryDelegate
import com.zakrodionov.protovary.data.entity.Researches
import kotlinx.android.synthetic.main.fragment_researches_category.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResearchesCategoryFragment : BaseFragment(R.layout.fragment_researches_category) {

    private val researchesCategoryViewModel: ResearchesCategoryViewModel by viewModel()
    private val researchesCategoryAdapter: ListDelegationAdapter<List<DisplayableItem>> by lazy {
        ListDelegationAdapter(researchCategoryDelegate(::itemClickListener))
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(researchesCategoryViewModel) {
            observe(researches, ::renderResearchesList)
            observeEvent(state, ::handleState)
        }

        initializeView()

        // Если отсутствовал интернет/либо по другой причине нет данных, пробуем их загрузить
        if (researchesCategoryViewModel.researches.value == null) {
            loadData()
        }
    }

    private fun initializeView() {
        tvToolbarTitle.text = getString(R.string.researches)

        rvResearches.layoutManager = LinearLayoutManager(activity)
        rvResearches.adapter = researchesCategoryAdapter
    }

    private fun itemClickListener(research: Researches) {
        val directions = ResearchesCategoryFragmentDirections.actionResearchesCategoryFragmentToResearchesFragment(research.id)
        navController.navigate(directions)
    }

    private fun renderResearchesList(research: List<Researches>?) {
        researchesCategoryAdapter.setData(research)

        tvEmpty?.isVisible = research.isNullOrEmpty()
        rvResearches?.isVisible = !research.isNullOrEmpty()
    }

    override fun onDestroyView() {
        rvResearches.adapter = null
        super.onDestroyView()
    }

    override fun loadData() = researchesCategoryViewModel.loadResearches()
}
