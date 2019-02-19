package com.zakrodionov.roskachestvo.app.ui.search

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ui.base.BaseFragment
import com.zakrodionov.roskachestvo.app.ui.base.Layout

@Layout(R.layout.view_search)
class SearchFragment : BaseFragment(), SearchView {

    //region Внедрение презентера
    @InjectPresenter
    lateinit var presenter: SearchPresenter

    @ProvidePresenter
    internal fun providePresenter(): SearchPresenter =
        SearchPresenter()
    //endregion

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun prepareUi(view: View) {

    }

}