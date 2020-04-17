package com.zakrodionov.protovary.app.ui.research

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Section
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.*
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.ui.research.items.DescItem
import com.zakrodionov.protovary.app.ui.research.items.ExpandableHeaderItem
import com.zakrodionov.protovary.app.ui.research.items.ProductItem
import com.zakrodionov.protovary.app.ui.researches.ResearchesFragmentArgs
import com.zakrodionov.protovary.app.ui.view.BottomDialogSortFragment
import com.zakrodionov.protovary.app.ui.view.BottomDialogSortFragment.BottomDialogSortListener
import com.zakrodionov.protovary.app.util.enums.ResearchFilterType.*
import com.zakrodionov.protovary.app.util.enums.ResearchSortType
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.android.synthetic.main.fragment_research.*
import kotlinx.android.synthetic.main.toolbar_search_and_filter.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ResearchFragment : BaseFragment(R.layout.fragment_research), BottomDialogSortListener {

    companion object {
        const val RC_SORT = 1122
        const val DIALOG_SORT_TAG = "dialog_sort_tag"
    }

    private val args: ResearchesFragmentArgs by navArgs()
    private val researchesId by lazy { args.researchId }

    private val groupAdapter = GroupAdapter<GroupieViewHolder>()
    private val section = Section()

    private val researchViewModel: ResearchViewModel by viewModel { parametersOf(researchesId) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(researchViewModel) {
            observe(researchDescription, ::renderResearchDescription)
            observe(filteredProducts, ::renderProductsList)
            observeEvent(state, ::handleState)
        }

        if (researchViewModel.filteredProducts.value == null) {
            loadData()
        }

        setupToolbar()
        setupChips()
        initializeRecycler()
    }

    private fun setupToolbar() {

        actionBack.setOnClickListener { back() }
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
                researchViewModel.queryText = newText
                scrollToTop()
                return false
            }
        })

        editText.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus -> if (hasFocus) tvTitle.gone() }

        tvTitle.text = getString(R.string.research)
    }

    private fun setupChips() {
        chipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipQualityMark -> researchViewModel.filterType = BY_QUALITY_MARK
                R.id.chipProductViolation -> researchViewModel.filterType = BY_PRODUCT_WITH_VIOLATION
                else -> researchViewModel.filterType = BY_DEFAULT
            }
        }
    }

    private fun initializeRecycler() {
        rvResearch.disableChangeAnimation()
        rvResearch.layoutManager = LinearLayoutManager(activity)
        rvResearch.adapter = groupAdapter.apply { add(section) }
    }

    private fun renderResearchDescription(desc: String?) {
        section.setHeader(ExpandableGroup(ExpandableHeaderItem).apply {
            add(DescItem(desc ?: ""))
        })
    }

    private fun renderProductsList(products: List<Product>?) {
        tvEmpty?.toggleVisibility(products.isNullOrEmpty())
        rvResearch?.toggleVisibility(!products.isNullOrEmpty())
        section.update(products?.map { ProductItem(it, ::itemClickListener, ::itemClickFavoriteListener) } ?: listOf())
    }

    private fun itemClickListener(product: Product) {
        closeSearch()
        val action = ResearchFragmentDirections.actionResearchFragmentToProductFragment(product.id)
        navController.navigate(action)
    }

    private fun closeSearch() {
        if (actionSearch.query.isNullOrEmpty()) {
            actionSearch.onActionViewCollapsed()
        }
    }

    private fun itemClickFavoriteListener(product: Product) {
        researchViewModel.actionFavorite(product)
    }

    private fun showBottomDialog() {
        val bottomSheetDialog = BottomDialogSortFragment.newInstance(
            researchViewModel.sortType
        )
        bottomSheetDialog.setTargetFragment(this, RC_SORT)
        bottomSheetDialog.show(requireFragmentManager(), DIALOG_SORT_TAG)
    }

    override fun onSortTypeSelected(sortType: ResearchSortType) {
        researchViewModel.sortType = sortType
        scrollToTop()
    }

    private fun scrollToTop() = rvResearch?.scrollToPosition(0)

    override fun onDestroyView() {
        rvResearch.adapter = null
        super.onDestroyView()
    }

    override fun loadData() = researchViewModel.loadResearch(researchesId)
}
