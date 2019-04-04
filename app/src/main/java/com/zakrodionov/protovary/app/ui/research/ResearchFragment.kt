package com.zakrodionov.protovary.app.ui.research

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
import com.zakrodionov.protovary.app.ui.research.adapter.ProductsAdapter
import com.zakrodionov.protovary.app.ui.view.BottomDialogSortFragment
import com.zakrodionov.protovary.app.ui.view.BottomDialogSortFragment.BottomDialogSortListener
import com.zakrodionov.protovary.app.ui.view.ListPaddingDecoration
import com.zakrodionov.protovary.app.util.enums.ResearchFilterType.*
import com.zakrodionov.protovary.app.util.enums.ResearchSortType
import com.zakrodionov.protovary.data.entity.ProductInfo
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.android.synthetic.main.toolbar_search_and_filter.*
import kotlinx.android.synthetic.main.view_research.*
import javax.inject.Inject


class ResearchFragment : BaseFragment(), BottomDialogSortListener {

    @Inject
    lateinit var productsAdapter: ProductsAdapter

    override fun layoutId() = R.layout.view_research

    private val idResearch: Long by argument("id", 0L)

    private lateinit var researchViewModel: ResearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        researchViewModel = viewModel(viewModelFactory) {}

        if (savedInstanceState.isFirstTimeCreated()) {
            researchViewModel.loadResearch(idResearch)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(researchViewModel) {
            observe(changesListener) { researchViewModel.applyChanges() }
            observe(filteredProducts, ::renderProductsList)
            observe(productsMediator) { }
            observe(loading, ::loadingStatus)
            failure(failure, ::handleFailure)
        }

        if (researchViewModel.filteredProducts.value == null) {
            researchViewModel.loadResearch(idResearch)
        }

        setupToolbar()
        setupChips()
        initializeRecycler()
    }

    private fun setupToolbar() {

        actionBack.setOnClickListener { close() }
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
                if (newText.isNotEmpty() || !actionSearch.isIconified) {
                    tvTitle.gone()
                }
                researchViewModel.queryText.value = newText
                scrollToTop()
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

    private fun renderProductsList(products: List<Product>?) {
        productsAdapter.updateUsingDiffUtil(products ?: listOf())
        productsAdapter.clickListener = ::itemClickListener
        productsAdapter.clickFavoriteListener = ::itemClickFavoriteListener

        tvEmpty?.toggleVisibility(products.isNullOrEmpty())
        rvResearch?.toggleVisibility(!products.isNullOrEmpty())
        rvResearch?.itemAnimator = null
    }

    private fun itemClickListener(product: Product) {
        actionSearch.onActionViewCollapsed()
        val bundle = bundleOf("id" to product.id)
        findNavController().navigate(R.id.action_researchFragment_to_productFragment, bundle)
    }

    private fun itemClickFavoriteListener(product: Product) {
        researchViewModel.actionFavorite(product)
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
        scrollToTop()
    }

    private fun scrollToTop() = rvResearch?.scrollToPosition(0)

    override fun onDestroyView() {
        rvResearch.adapter = null
        super.onDestroyView()
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