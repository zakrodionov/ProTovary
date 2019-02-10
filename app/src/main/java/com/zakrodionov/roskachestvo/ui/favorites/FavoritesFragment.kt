package com.zakrodionov.roskachestvo.ui.favorites

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.common.BaseFragment
import com.zakrodionov.roskachestvo.common.Layout
import com.zakrodionov.roskachestvo.presentation.favorites.FavoritesPresenter
import com.zakrodionov.roskachestvo.presentation.favorites.FavoritesView

@Layout(R.layout.view_favorites)
class FavoritesFragment : BaseFragment(), FavoritesView {

    //region Внедрение презентера
    @InjectPresenter
    lateinit var presenter: FavoritesPresenter

    @ProvidePresenter
    internal fun providePresenter(): FavoritesPresenter =
        FavoritesPresenter()
    //endregion

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun prepareUi(view: View) {

    }

}