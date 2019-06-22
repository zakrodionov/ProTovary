package com.zakrodionov.protovary.app.ui.researchescategory

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.observe
import com.zakrodionov.protovary.app.ext.toggleVisibility
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.ui.view.ListPaddingDecoration
import com.zakrodionov.protovary.data.entity.Researches
import kotlinx.android.synthetic.main.toolbar_main.*
import kotlinx.android.synthetic.main.view_researches_category.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ResearchesCategoryFragment : BaseFragment(R.layout.view_researches_category) {

    private val researchesCategoryViewModel: ResearchesCategoryViewModel  by viewModel()
    private val researchesCategoryAdapter: ResearchesCategoryAdapter by lazy { ResearchesCategoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Загружаем только при первом создании фрагмента
        if (savedInstanceState.isFirstTimeCreated()) {
            loadResearchList()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(researchesCategoryViewModel) {
            observe(researches, ::renderResearchesList)
            observe(state, ::handleState)
        }

        initializeView()

        //Если отсутствовал интернет/либо по другой причине нет данных, пробуем их загрузить
        if (researchesCategoryViewModel.researches.value == null) {
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
        navController.navigate(R.id.action_researchesCategoryFragment_to_researchesFragment, bundle)
    }

    private fun loadResearchList() {
        researchesCategoryViewModel.loadResearches()
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

//    private fun handleFailure(failure: Failure?) {
//        when (failure) {
//            is Failure.ServerError -> notify(R.string.failure_server_error)
//            is Failure.UnknownError -> notify(R.string.failure_unknown_error)
//            is Failure.NetworkConnection -> notifyWithAction(
//                R.string.failure_network_connection,
//                R.string.action_refresh,
//                ::loadResearchList
//            )
//            is Failure.CacheFailure<*> -> notifyWithAction(
//                R.string.failure_cache_date, R.string.action_refresh, ::loadResearchList,
//                Snackbar.LENGTH_SHORT
//            )
//        }
//    }
}