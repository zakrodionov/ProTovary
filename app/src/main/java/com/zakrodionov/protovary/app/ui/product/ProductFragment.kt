package com.zakrodionov.protovary.app.ui.product

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.loadFromUrl
import com.zakrodionov.protovary.app.ext.observe
import com.zakrodionov.protovary.app.ext.observeEvent
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.ui.product.pager.DescriptionPagerAdapter
import com.zakrodionov.protovary.data.entity.ProductDetail
import kotlinx.android.synthetic.main.fragment_product.*
import kotlinx.android.synthetic.main.fragment_product.view.*
import kotlinx.android.synthetic.main.toolbar_back_favorite_share.*
import org.jetbrains.anko.support.v4.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class ProductFragment : BaseFragment(R.layout.fragment_product) {

    private val args: ProductFragmentArgs by navArgs()
    private val productId by lazy { args.productId }
    private val productViewModel: ProductViewModel by viewModel { parametersOf(productId) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        val view = inflater.inflate(R.layout.fragment_product, container, false);
        // When the image is loaded, set the image request listener to start the transaction
        val imageRequestListener = object: RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                startPostponedEnterTransition()
                return false
            }
        }
        view.ivCollapsingToolbar.transitionName = productId.toString()
        Glide.with(this).load(arguments?.getString("imageUrl")).listener(imageRequestListener).into(view.ivCollapsingToolbar)
        // Make sure we don't wait longer than a second for the image request
        postponeEnterTransition(1, TimeUnit.SECONDS)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(productViewModel) {
            observe(product, ::renderProduct)
            observe(isFavorite, ::renderFavorite)
            observeEvent(message, ::renderMessage)
            observeEvent(state, ::handleState)
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
       // ivCollapsingToolbar.loadFromUrl("${BuildConfig.API_IMAGE_URL}${product?.image?.src}")
        tvTitle.text = product?.name?.parseAsHtml()

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
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, getString(R.string.share_with)))
    }

    override fun onDestroyView() {
        viewpager.adapter = null
        super.onDestroyView()
    }

    override fun loadData() = productViewModel.loadProduct(productId)
}