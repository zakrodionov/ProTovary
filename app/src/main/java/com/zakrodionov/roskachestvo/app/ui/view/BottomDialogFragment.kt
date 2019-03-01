package com.zakrodionov.roskachestvo.app.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType

class BottomDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_sort_research, container, false)
        return view
    }

    companion object {
        fun newInstance(selectedResearchType: ResearchSortType): BottomDialogFragment {
            val dialog = BottomDialogFragment()
            val bundle = Bundle().apply { putSerializable("type", selectedResearchType) }
            dialog.arguments = bundle
            return dialog
        }
    }

    interface BottomDialogSortListener{
        fun onSortTypeSelected(sortType: ResearchSortType)
    }
}