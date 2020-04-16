package com.zakrodionov.protovary.app.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.observe
import com.zakrodionov.protovary.app.ext.observeEvent
import com.zakrodionov.protovary.app.ext.setData
import com.zakrodionov.protovary.app.platform.BaseFragment
import com.zakrodionov.protovary.app.platform.DiffItem
import com.zakrodionov.protovary.app.ui.research.adapter.productDelegate
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.android.synthetic.main.fragment_favorites.*
import kotlinx.android.synthetic.main.toolbar_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private val productsFavoriteAdapter: ListDelegationAdapter<List<DiffItem>> by lazy {
        ListDelegationAdapter(productDelegate(::itemClickListener, ::actionFavoriteListener))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(favoritesViewModel) {
            observe(favoriteProducts, ::handleFavoriteProductsList)
            observeEvent(state, ::handleState)
        }

        initializeView()
    }

    private fun handleFavoriteProductsList(products: List<Product>?) {
        productsFavoriteAdapter.setData(products)

        tvEmpty?.isVisible = products.isNullOrEmpty()
        rvProductsFavorite?.isVisible = !products.isNullOrEmpty()
    }

    private fun itemClickListener(research: Product) {
        val directions =
            FavoritesFragmentDirections.actionFavoritesFragmentToProductFragment(research.id)
        navController.navigate(directions)
    }

    private fun actionFavoriteListener(research: Product) {
        favoritesViewModel.actionFavorite(research)
    }

    private fun initializeView() {
        tvToolbarTitle.text = getString(R.string.favorites)

        rvProductsFavorite.layoutManager = LinearLayoutManager(activity)
        rvProductsFavorite.adapter = productsFavoriteAdapter
    }

    override fun onDestroyView() {
        rvProductsFavorite.adapter = null
        super.onDestroyView()
    }
}
