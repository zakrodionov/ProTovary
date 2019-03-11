package com.zakrodionov.roskachestvo.app.ui.product

import android.os.Bundle
import android.view.View
import com.zakrodionov.roskachestvo.BuildConfig
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.*
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.app.ui.product.pager.DescriptionPagerAdapter
import com.zakrodionov.roskachestvo.domain.entity.Product
import kotlinx.android.synthetic.main.failure_holder.*
import kotlinx.android.synthetic.main.view_product.*


class ProductFragment : BaseFragment() {


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
        collapsing_toolbar_image_view.loadFromUrl("${BuildConfig.API_ENDPOINT.substringBeforeLast("api/")}${product?.image?.src}")
        //collapsing_toolbar.visible()

        product?.let {
            val pagerAdapter = DescriptionPagerAdapter(
                activity!!,
                product,
                childFragmentManager
            )

            viewpager.adapter = pagerAdapter
            viewpager.offscreenPageLimit = 4
            tabs.setupWithViewPager(viewpager)
        }

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