package com.zakrodionov.roskachestvo.app.ui.product

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.*
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import com.zakrodionov.roskachestvo.app.platform.Failure
import kotlinx.android.synthetic.main.failure_holder.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.roskachestvo.app.ui.view.BottomDialogFragment
import com.zakrodionov.roskachestvo.app.ui.view.BottomDialogFragment.BottomDialogSortListener
import com.zakrodionov.roskachestvo.app.ui.view.ListPaddingDecoration
import kotlinx.android.synthetic.main.view_research.*
import com.zakrodionov.roskachestvo.app.util.enums.ResearchFilterType.*
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType
import com.zakrodionov.roskachestvo.domain.entity.ProductsInfo
import kotlinx.android.synthetic.main.toolbar_search_and_filter.*
import javax.inject.Inject


class DescriptionFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.view_description, container, false)
        return view
    }
}