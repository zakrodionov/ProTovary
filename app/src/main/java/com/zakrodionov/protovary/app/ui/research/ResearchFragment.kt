package com.zakrodionov.protovary.app.ui.research

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.*
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.app.ui.view.BottomDialogSortFragment
import com.zakrodionov.protovary.app.ui.view.BottomDialogSortFragment.BottomDialogSortListener
import com.zakrodionov.protovary.app.ui.view.ListPaddingDecoration
import com.zakrodionov.protovary.app.util.enums.ResearchFilterType.*
import com.zakrodionov.protovary.app.util.enums.ResearchSortType
import com.zakrodionov.protovary.domain.entity.ProductInfo
import kotlinx.android.synthetic.main.toolbar_search_and_filter.*
import kotlinx.android.synthetic.main.view_research.*
import javax.inject.Inject


class ResearchFragment : BaseFragment(), BottomDialogSortListener {

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    override fun layoutId() = R.layout.view_research
    override fun navigationLayoutId() = R.id.hostFragment

    private val idResearch: Long by argument("id", 0L)

    private lateinit var researchViewModel: ResearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        researchViewModel = viewModel(viewModelFactory) {
            observe(changesListener) { researchViewModel.applyChanges() }
            observe(filteredProducts, ::renderProductsList)
            observe(productsMediator) { }
            observe(loading, ::loadingStatus)
            failure(failure, ::handleFailure)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        researchViewModel.loadResearch(idResearch)
        setupToolbar()
        setupChips()
        initializeRecycler()

    }

    private fun setupToolbar() {

        actionBack.setOnClickListener { navController.popBackStack() }
        actionSort.setOnClickListener { showBottomDialog() }

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
                researchViewModel.queryText.value = newText
                return false
            }
        })

        editText.setOnFocusChangeListener { v, hasFocus -> if (hasFocus) tvTitle.gone() }

        tvTitle.text = getString(R.string.research)

    }

    private fun setupChips() {
        chipQualityMark.setTextAppearanceResource(R.style.textChipStyle)
        chipProductViolation.setTextAppearanceResource(R.style.textChipStyle)

        chipGroup.isSingleSelection = true

        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipQualityMark -> researchViewModel.filterType.value = BY_QUALITY_MARK
                R.id.chipProductViolation -> researchViewModel.filterType.value = BY_PRODUCT_WITH_VIOLATION
                else -> researchViewModel.filterType.value = BY_DEFAULT
            }
        }
    }

    private fun initializeRecycler() {
        rvResearch.addItemDecoration(ListPaddingDecoration(activity!!))
        rvResearch.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvResearch.adapter = productsAdapter
    }

    private fun renderProductsList(products: List<ProductInfo>?) {
        productsAdapter.collection = products ?: listOf()
        productsAdapter.clickListener = ::itemClickListener
        productsAdapter.clickFavoriteListener = ::itemClickFavoriteListener

        tvEmpty?.toggleVisibility(products.isNullOrEmpty())
        rvResearch?.toggleVisibility(!products.isNullOrEmpty())
    }

    private fun itemClickListener(productInfo: ProductInfo) {
        val bundle = Bundle().apply { putLong("id", productInfo.id) }
        navController.navigate(R.id.action_researchFragment_to_productFragment, bundle)
    }

    private fun itemClickFavoriteListener(productInfo: ProductInfo) {
        researchViewModel.actionFavorite(productInfo)
    }

    private fun showBottomDialog() {
        val bottomSheetDialog =
            BottomDialogSortFragment.newInstance(
                researchViewModel.sortType.value ?: ResearchSortType.BY_RATING_DECREASE
            )
        bottomSheetDialog.setTargetFragment(this, RC_SORT)
        bottomSheetDialog.show(fragmentManager!!, "Dialog Sort")
    }

    override fun onSortTypeSelected(sortType: ResearchSortType) {
        researchViewModel.sortType.value = sortType
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
            is Failure.UnknownError -> notify(R.string.failure_unknown_error)
            is Failure.NetworkConnection -> notifyWithAction(
                R.string.failure_network_connection,
                R.string.action_refresh,
                { researchViewModel.loadResearch(idResearch) }
            )
        }
    }

    companion object {
        const val RC_SORT = 1122
    }
}