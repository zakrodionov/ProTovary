package com.zakrodionov.protovary.app.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.observe
import com.zakrodionov.protovary.app.ext.toggleVisibility
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.ui.view.ListPaddingDecoration
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.android.synthetic.main.toolbar_main.*
import kotlinx.android.synthetic.main.view_favorites.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : BaseFragment(R.layout.view_favorites) {

    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private val productsFavoriteAdapter: ProductsFavoriteAdapter by lazy { ProductsFavoriteAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(favoritesViewModel) {
            observe(favoriteProducts, ::renderFavoriteProductsList)
            observe(state, ::handleState)
        }

        initializeView()
    }

    private fun renderFavoriteProductsList(products: List<Product>?) {
        productsFavoriteAdapter.collection = products ?: listOf()
        productsFavoriteAdapter.clickListener = ::itemClickListener
        productsFavoriteAdapter.actionFavoriteListener = ::actionFavoriteListener

        tvEmpty?.toggleVisibility(products.isNullOrEmpty())
        rvProductsFavorite?.toggleVisibility(!products.isNullOrEmpty())

    }

    private fun itemClickListener(research: Product) {
        val bundle = bundleOf("id" to research.id)
        navController.navigate(R.id.action_favoritesFragment_to_productFragment, bundle)
    }

    private fun actionFavoriteListener(research: Product) {
        favoritesViewModel.actionFavorite(research)
    }

    private fun initializeView() {
        tvToolbarTitle.text = getString(R.string.favorites)

        rvProductsFavorite.addItemDecoration(ListPaddingDecoration(activity!!))
        rvProductsFavorite.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvProductsFavorite.adapter = productsFavoriteAdapter
    }

    override fun onDestroyView() {
        rvProductsFavorite.adapter = null
        super.onDestroyView()
    }
//    private fun handleFailure(failure: Failure?) {
//        when (failure) {
//            is Failure.ServerError -> notify(R.string.failure_server_error)
//            is Failure.UnknownError -> notify(R.string.failure_unknown_error)
//        }
//    }
}