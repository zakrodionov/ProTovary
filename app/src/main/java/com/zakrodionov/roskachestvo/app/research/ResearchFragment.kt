package com.zakrodionov.roskachestvo.app.research

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.common.BaseFragment
import com.zakrodionov.roskachestvo.common.Layout

@Layout(R.layout.view_research)
class ResearchFragment : BaseFragment(), ResearchView {

    //region Внедрение презентера
    @InjectPresenter
    lateinit var presenter: ResearchPresenter

    @ProvidePresenter
    internal fun providePresenter(): ResearchPresenter =
        ResearchPresenter()
    //endregion

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun prepareUi(view: View) {

    }

}