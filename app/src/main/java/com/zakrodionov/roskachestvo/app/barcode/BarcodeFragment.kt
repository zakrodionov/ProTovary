package com.zakrodionov.roskachestvo.app.barcode

import android.os.Bundle
import android.view.View
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.common.BaseFragment
import com.zakrodionov.roskachestvo.common.Layout

@Layout(R.layout.view_barcode)
class BarcodeFragment : BaseFragment(), BarcodeView {

    //region Внедрение презентера
    @InjectPresenter
    lateinit var presenter: BarcodePresenter

    @ProvidePresenter
    internal fun providePresenter(): BarcodePresenter =
        BarcodePresenter()
    //endregion

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

    override fun prepareUi(view: View) {

    }

}