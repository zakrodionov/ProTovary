package com.zakrodionov.protovary.app.ui.researchescategory

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.observe
import com.zakrodionov.protovary.app.ext.toggleVisibility
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.ui.researchescategory.adapter.ResearchesCategoryAdapter
import com.zakrodionov.protovary.app.ui.view.ListPaddingDecoration
import com.zakrodionov.protovary.data.entity.Researches
import kotlinx.android.synthetic.main.toolbar_main.*
import kotlinx.android.synthetic.main.view_researches_category.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResearchesCategoryFragment : BaseFragment(R.layout.view_researches_category) {

    private val researchesCategoryViewModel: ResearchesCategoryViewModel by viewModel()
    private val researchesCategoryAdapter: ResearchesCategoryAdapter by lazy { ResearchesCategoryAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(researchesCategoryViewModel) {
            observe(researches, ::renderResearchesList)
            observe(state, ::handleState)
        }

        initializeView()

        //Если отсутствовал интернет/либо по другой причине нет данных, пробуем их загрузить
        if (researchesCategoryViewModel.researches.value == null) {
            loadData()
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
        val directions = ResearchesCategoryFragmentDirections.actionResearchesCategoryFragmentToResearchesFragment(research.id)
        navController.navigate(directions)
    }

    private fun renderResearchesList(research: List<Researches>?) {
        researchesCategoryAdapter.collection = research.orEmpty()

        tvEmpty?.toggleVisibility(research.isNullOrEmpty())
        rvResearches?.toggleVisibility(!research.isNullOrEmpty())
    }

    override fun onDestroyView() {
        rvResearches.adapter = null
        super.onDestroyView()
    }

    override fun loadData() = researchesCategoryViewModel.loadResearches()

}