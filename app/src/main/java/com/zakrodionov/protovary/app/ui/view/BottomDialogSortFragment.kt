package com.zakrodionov.protovary.app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.util.enums.ResearchSortType
import com.zakrodionov.protovary.app.util.enums.ResearchSortType.*
import kotlinx.android.synthetic.main.dialog_sort_research.view.*

class BottomDialogSortFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_sort_research, container, false)
        val type = arguments?.getSerializable("type") ?: BY_RATING_DECREASE

        when (type) {
            BY_RATING_DECREASE -> view.rlRatingDecrease.isSelected = true
            BY_RATING_INCREASE -> view.rlRatingIncrease.isSelected = true
            BY_TRADEMARK -> view.rlTradeMark.isSelected = true
        }

        val parentFragment = targetFragment as? BottomDialogSortListener

        view.rlRatingDecrease.setOnClickListener { parentFragment?.onSortTypeSelected(ResearchSortType.BY_RATING_DECREASE); dismiss() }
        view.rlRatingIncrease.setOnClickListener { parentFragment?.onSortTypeSelected(ResearchSortType.BY_RATING_INCREASE); dismiss() }
        view.rlTradeMark.setOnClickListener { parentFragment?.onSortTypeSelected(ResearchSortType.BY_TRADEMARK); dismiss() }

        return view
    }

    companion object {
        fun newInstance(selectedResearchType: ResearchSortType): BottomDialogSortFragment {
            val dialog = BottomDialogSortFragment()
            val bundle = Bundle().apply { putSerializable("type", selectedResearchType) }
            dialog.arguments = bundle
            return dialog
        }
    }

    interface BottomDialogSortListener {
        fun onSortTypeSelected(sortType: ResearchSortType)
    }
}