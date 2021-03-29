package com.zakrodionov.protovary.app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.visible
import com.zakrodionov.protovary.app.util.enums.ResearchSortType
import com.zakrodionov.protovary.app.util.enums.ResearchSortType.*
import kotlinx.android.synthetic.main.dialog_sort_research.view.*

class BottomDialogSortFragment : BottomSheetDialogFragment() {

    override fun getTheme(): Int = R.style.AppBottomSheetDialogTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_sort_research, container, false)
        val type = arguments?.getSerializable(ARG_SORT_TYPE) ?: BY_RATING_DECREASE

        when (type) {
            BY_RATING_DECREASE -> {
                view.rlRatingDecrease.isSelected = true
                view.ivRatingDecreaseDone.visible()
            }
            BY_RATING_INCREASE -> {
                view.rlRatingIncrease.isSelected = true
                view.ivRatingIncreaseDone.visible()
            }
            BY_TRADEMARK -> {
                view.rlTradeMark.isSelected = true
                view.ivTradeMarkDone.visible()
            }
        }

        val parentFragment = targetFragment as? BottomDialogSortListener

        view.rlRatingDecrease.setOnClickListener { parentFragment?.onSortTypeSelected(BY_RATING_DECREASE); dismiss() }
        view.rlRatingIncrease.setOnClickListener { parentFragment?.onSortTypeSelected(BY_RATING_INCREASE); dismiss() }
        view.rlTradeMark.setOnClickListener { parentFragment?.onSortTypeSelected(BY_TRADEMARK); dismiss() }

        return view
    }

    companion object {
        private const val ARG_SORT_TYPE = "arg_sort_type"

        fun newInstance(selectedResearchType: ResearchSortType) = BottomDialogSortFragment().apply {
            arguments = bundleOf(ARG_SORT_TYPE to selectedResearchType)
        }
    }

    interface BottomDialogSortListener {
        fun onSortTypeSelected(sortType: ResearchSortType)
    }
}
