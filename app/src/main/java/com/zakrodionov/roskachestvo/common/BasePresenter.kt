package com.zakrodionov.roskachestvo.common

import com.arellomobile.mvp.MvpPresenter
import io.reactivex.disposables.CompositeDisposable

class BasePresenter : MvpPresenter<BaseView>() {
    protected val disposables = CompositeDisposable()

    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
}