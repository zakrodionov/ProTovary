package com.zakrodionov.roskachestvo.app.ui.research

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.*
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import com.zakrodionov.roskachestvo.app.platform.Failure
import kotlinx.android.synthetic.main.failure_holder.*
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.roskachestvo.app.ui.view.BottomDialogFragment
import com.zakrodionov.roskachestvo.app.ui.view.BottomDialogFragment.BottomDialogSortListener
import com.zakrodionov.roskachestvo.app.ui.view.ListPaddingDecoration
import kotlinx.android.synthetic.main.view_research.*
import com.zakrodionov.roskachestvo.app.util.enums.ResearchFilterType
import com.zakrodionov.roskachestvo.app.util.enums.ResearchFilterType.*
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType.*
import com.zakrodionov.roskachestvo.domain.entity.ProductsInfo
import kotlinx.android.synthetic.main.toolbar_search_and_filter.*
import kotlinx.android.synthetic.main.view_research.view.*
import kotlinx.android.synthetic.main.view_researches.*
import javax.inject.Inject


class ResearchFragment : BaseFragment(), BottomDialogSortListener {

    @Inject
    lateinit var researchAdapter: ResearchAdapter

    override fun layoutId() = R.layout.view_research
    override fun failureHolderId() = R.id.failureHolder
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var researchViewModel: ResearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        researchViewModel = viewModel(viewModelFactory) {
            observe(changesListener) { researchViewModel.applyChanges() }
            observe(filteredResearch, ::renderResearchList)
            observe(loading, ::loadingStatus)
            failure(failure, ::handleFailure)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        researchViewModel.loadResearch(arguments?.getLong("id", 0L) ?: 0L)
        setupToolbar()
        setupChips()
        initializeRecycler()

    }

    private fun setupToolbar() {

        actionBack.setOnClickListener { navController.popBackStack() }
        actionSort.setOnClickListener { showBottomDialog() }

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
                researchViewModel.queryTextChange(newText)
                return false
            }
        })
    }

    private fun setupChips() {
        chipQualityMark.setTextAppearanceResource(R.style.textChipStyle)
        chipProductViolation.setTextAppearanceResource(R.style.textChipStyle)

        chipGroup.isSingleSelection = true

        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId){
                R.id.chipQualityMark -> researchViewModel.setFilterType(QUALITY_MARK)
                R.id.chipProductViolation -> researchViewModel.setFilterType(PRODUCT_WITH_VIOLATION)
                else -> researchViewModel.setFilterType(BY_DEFAULT)
            }
        }
    }

    private fun initializeRecycler() {
        rvResearch.addItemDecoration(ListPaddingDecoration(activity!!))
        rvResearch.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvResearch.adapter = researchAdapter
    }

    private fun renderResearchList(research: List<ProductsInfo>?) {
        researchAdapter.collection = research ?: listOf()
        //researchAdapter.clickListener = ::itemClickListener
        failureHolder?.gone()
    }

    private fun showBottomDialog(){
        val bottomSheetDialog = BottomDialogFragment.newInstance(researchViewModel.sortType.value ?: ResearchSortType.BY_RATING_DECREASE)
        bottomSheetDialog.setTargetFragment(this, RC_SORT)
        bottomSheetDialog.show(fragmentManager!!, "Dialog Sort")
    }

    override fun onSortTypeSelected(sortType: ResearchSortType) {
        researchViewModel.setSortType(sortType)
    }

    private fun handleFailure(failure: Failure?) {
        failureHolder?.visible()
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
            is Failure.UnknownError -> notify(R.string.failure_unknown_error)
        }
    }

    companion object {
       const val RC_SORT = 1122
    }
}