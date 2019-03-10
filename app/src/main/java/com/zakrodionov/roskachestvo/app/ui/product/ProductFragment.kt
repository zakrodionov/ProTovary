package com.zakrodionov.roskachestvo.app.ui.product

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
import com.zakrodionov.roskachestvo.app.util.enums.ResearchFilterType.*
import com.zakrodionov.roskachestvo.app.util.enums.ResearchSortType
import com.zakrodionov.roskachestvo.domain.entity.Product
import com.zakrodionov.roskachestvo.domain.entity.ProductsInfo
import kotlinx.android.synthetic.main.toolbar_search_and_filter.*
import javax.inject.Inject


class ProductFragment : BaseFragment(){


    override fun layoutId() = R.layout.view_product
    override fun failureHolderId() = R.id.failureHolder
    override fun navigationLayoutId() = R.id.hostFragment

    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        productViewModel = viewModel(viewModelFactory) {
            observe(product, ::renderProduct)
            observe(loading, ::loadingStatus)
            failure(failure, ::handleFailure)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.loadProduct(arguments?.getLong("id", 0L) ?: 0L)

    }





    private fun renderProduct(product: Product?) {
        failureHolder?.gone()
    }


    private fun handleFailure(failure: Failure?) {
        failureHolder?.visible()
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
            is Failure.UnknownError -> notify(R.string.failure_unknown_error)
        }
    }

}