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
import kotlinx.android.synthetic.main.toolbar_search.*
import android.widget.ImageView
import kotlinx.android.synthetic.main.view_research.*
import org.jetbrains.anko.support.v4.toast
import com.zakrodionov.roskachestvo.app.ui.view.BottomDialogFragment
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType


class ResearchFragment : BaseFragment() {


    override fun layoutId() = R.layout.view_research
    override fun failureHolderId() = R.id.failureHolder
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var researchViewModel: ResearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        researchViewModel = viewModel(viewModelFactory) {
            observe(changesListener) { researchViewModel.applyChanges() }
            observe(loading, ::loadingStatus)
            failure(failure, ::handleFailure)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupChips()
    }

    private fun setupToolbar() {

        actionBack.setOnClickListener { navController.popBackStack() }

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
        chipQuality.setTextAppearanceResource(R.style.textChipStyle)
        chipPoor.setTextAppearanceResource(R.style.textChipStyle)

        chipGroup.isSingleSelection = true

        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            toast(checkedId.toString())
            val bottomSheetDialog = BottomDialogFragment.newInstance(researchViewModel.sortType.value ?: ResearchSortType.BY_RATING_DECREASE)
            bottomSheetDialog.setTargetFragment(this, RC_SORT)
            bottomSheetDialog.show(childFragmentManager, "Custom Bottom Sheet")
        }
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