package com.zakrodionov.protovary.app.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.failure
import com.zakrodionov.protovary.app.ext.observe
import com.zakrodionov.protovary.app.ext.toggleVisibility
import com.zakrodionov.protovary.app.ext.viewModel
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.platform.Failure
import com.zakrodionov.protovary.app.ui.view.ListPaddingDecoration
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import kotlinx.android.synthetic.main.toolbar_main.*
import kotlinx.android.synthetic.main.view_favorites.*
import javax.inject.Inject

class FavoritesFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_favorites
    override fun navigationLayoutId() = R.id.hostFragment

    @Inject
    lateinit var productsFavoriteAdapter: ProductsFavoriteAdapter

    private lateinit var favoritesViewModel: FavoritesViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        favoritesViewModel = viewModel(viewModelFactory) {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(favoritesViewModel) {
            observe(favoriteProducts, ::renderFavoriteProductsList)
            failure(failure, ::handleFailure)
        }

        initializeView()
    }

    private fun renderFavoriteProductsList(products: List<FavoriteProduct>?) {
        productsFavoriteAdapter.collection = products ?: listOf()
        productsFavoriteAdapter.clickListener = ::itemClickListener
        productsFavoriteAdapter.actionFavoriteListener = ::actionFavoriteListener

        tvEmpty?.toggleVisibility(products.isNullOrEmpty())
        rvProductsFavorite?.toggleVisibility(!products.isNullOrEmpty())

    }

    private fun itemClickListener(research: FavoriteProduct) {
        val bundle = bundleOf("id" to research.id)
        findNavController().navigate(R.id.action_favoritesFragment_to_productFragment, bundle)
    }

    private fun actionFavoriteListener(research: FavoriteProduct) {
        favoritesViewModel.actionFavorite(research)
    }

    private fun initializeView() {
        tvToolbarTitle.text = getString(R.string.favorites)

        rvProductsFavorite.addItemDecoration(ListPaddingDecoration(activity!!))
        rvProductsFavorite.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvProductsFavorite.adapter = productsFavoriteAdapter
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
            is Failure.UnknownError -> notify(R.string.failure_unknown_error)
        }
    }
}