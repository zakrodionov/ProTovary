package com.zakrodionov.roskachestvo.app.ui.favorites

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.*
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import com.zakrodionov.roskachestvo.app.platform.Failure
import com.zakrodionov.roskachestvo.app.ui.view.ListPaddingDecoration
import com.zakrodionov.roskachestvo.data.db.entity.FavoriteProduct
import kotlinx.android.synthetic.main.failure_holder.*
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

        favoritesViewModel = viewModel(viewModelFactory) {
            observe(favoriteProducts, ::renderFavoriteProductsList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
    }

    private fun renderFavoriteProductsList(products: List<FavoriteProduct>?) {
        productsFavoriteAdapter.collection = products ?: listOf()
        productsFavoriteAdapter.clickListener = ::itemClickListener
        productsFavoriteAdapter.actionFavoriteListener = ::actionFavoriteListener

        tvEmpty?.toggleVisibility(products.isNullOrEmpty())
        rvProductsFavorite?.toggleVisibility(!products.isNullOrEmpty())
        failureHolder?.gone()

    }

    private fun itemClickListener(research: FavoriteProduct) {
        val bundle = Bundle().apply { putLong("id", research.id) }
        navController.navigate(R.id.action_favoritesFragment_to_productFragment, bundle)
    }

    private fun actionFavoriteListener(research: FavoriteProduct) {
        favoritesViewModel.deleteFromStore(research.id)
    }

    private fun initializeView() {
        tvToolbarTitle.text = getString(R.string.favorites)

        rvProductsFavorite.addItemDecoration(ListPaddingDecoration(activity!!))
        rvProductsFavorite.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvProductsFavorite.adapter = productsFavoriteAdapter
    }

    private fun handleFailure(failure: Failure?) {
        failureHolder?.visible()
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
            is Failure.UnknownError -> notify(R.string.failure_unknown_error)
        }
    }
}