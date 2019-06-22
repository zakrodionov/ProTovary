package com.zakrodionov.protovary.app.ui.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.argument
import com.zakrodionov.protovary.app.ext.loadFromUrl
import com.zakrodionov.protovary.app.ext.observe
import com.zakrodionov.protovary.app.ext.parseHtml
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.ui.product.pager.DescriptionPagerAdapter
import com.zakrodionov.protovary.data.entity.ProductDetail
import kotlinx.android.synthetic.main.toolbar_back_favorite_share.*
import kotlinx.android.synthetic.main.view_product.*
import org.jetbrains.anko.support.v4.toast
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProductFragment : BaseFragment(R.layout.view_product) {

    private val productViewModel: ProductViewModel by viewModel()
    private val productId: Long by argument("id", 0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState.isFirstTimeCreated()) {
            productViewModel.loadProduct(productId)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(productViewModel) {
            observe(product, ::renderProduct)
            observe(isFavoriteMediator, ::renderFavorite)
            observe(message, ::renderMessage)
            observe(state, ::handleState)
        }

        if (productViewModel.product.value == null) {
            productViewModel.loadProduct(productId)
        }

        setupToolbar()
    }

    private fun setupToolbar() {
        actionBack.setOnClickListener { back() }
        actionFavorite.setOnClickListener { productViewModel.actionFavorite(productId) }
        actionShare.setOnClickListener { productViewModel.product.value?.url?.let { shareProduct("${getString(R.string.base_url)}$it") } }
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


    private fun renderProduct(product: ProductDetail?) {
        ivCollapsingToolbar.loadFromUrl("${BuildConfig.API_ENDPOINT.substringBeforeLast("api/")}${product?.image?.src}")
        tvTitle.text = product?.name?.parseHtml()

        when (product?.status) {
            getString(R.string.status_sign) -> ivStatus.setImageResource(R.drawable.quality_sign)
            getString(R.string.status_violation) -> ivStatus.setImageResource(R.drawable.with_violation)
            else -> ivStatus.setImageResource(0)
        }
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

    }

    private fun shareProduct(text: String) {
        val intent = Intent(android.content.Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(android.content.Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, getString(R.string.share_with)))
    }

    override fun onDestroyView() {
        viewpager.adapter = null
        super.onDestroyView()
    }

//    private fun handleFailure(failure: Failure?) {
//        when (failure) {
//            is Failure.ServerError -> notify(R.string.failure_server_error)
//            is Failure.UnknownError -> notify(R.string.failure_unknown_error)
//            is Failure.NetworkConnection -> notifyWithAction(
//                R.string.failure_network_connection,
//                R.string.action_refresh,
//                { productViewModel.loadProduct(productId) }
//            )
//        }
//    }

}