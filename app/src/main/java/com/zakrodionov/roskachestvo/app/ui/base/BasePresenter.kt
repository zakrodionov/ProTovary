package com.zakrodionov.roskachestvo.app.ui.base

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable

abstract class BasePresenter<View : BaseView> : MvpPresenter<View>() {
    private val disposables = CompositeDisposable()

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}