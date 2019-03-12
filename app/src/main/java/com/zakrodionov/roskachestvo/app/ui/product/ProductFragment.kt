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
import kotlinx.android.synthetic.main.toolbar_back_favorite_share.*
import kotlinx.android.synthetic.main.view_product.*
import org.jetbrains.anko.support.v4.toast


class ProductFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_product
    override fun failureHolderId() = R.id.failureHolder
    override fun navigationLayoutId() = R.id.hostFragment

    private val productId: Long by argument("id", 0L)

    private lateinit var productViewModel: ProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        productViewModel = viewModel(viewModelFactory) {
            observe(product, ::renderProduct)
            observe(loading, ::loadingStatus)
            observe(isFavoriteMediator, ::renderFavorite)
            observe(message, ::renderMessage)
            failure(failure, ::handleFailure)
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productViewModel.loadProduct(productId)
        setupToolbar()
    }

    private fun setupToolbar() {
        actionFavorite.setOnClickListener { productViewModel.actionFavorite(productId) }
    }

    private fun renderFavorite(isFavorite: Boolean?) {
        if (isFavorite == true)
            actionFavorite.setImageResource(R.drawable.ic_favorite)
        else
            actionFavorite.setImageResource(R.drawable.ic_favorite_border)

    }

    private fun renderMessage(message: String?) {
      message?.let { toast(it) }
    }


    private fun renderProduct(product: Product?) {
        collapsing_toolbar_image_view.loadFromUrl("${BuildConfig.API_ENDPOINT.substringBeforeLast("api/")}${product?.image?.src}")

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