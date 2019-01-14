package com.zakrodionov.roskachestvo.common

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zakrodionov.roskachestvo.extensions.ContextAware
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : MvpAppXFragment(), BaseView, ContextAware {
    private var disposables = CompositeDisposable()
    private val viewHandler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        javaClass.getAnnotation(Layout::class.java)?.let {
            val view = inflater.inflate(it.value, container, false)
            prepareUi(view)
            return view
        }
        throw RuntimeException()
    }

    //fix for async views (like swipeToRefresh and RecyclerView)
    //if synchronously call actions on swipeToRefresh in sequence show and hide then swipeToRefresh will not hidden
    protected fun postViewAction(action: () -> Unit) {
        viewHandler.post(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewHandler.removeCallbacksAndMessages(null)
    }

    override fun getContext(): Context = super.getContext()!!

    abstract fun prepareUi(view: View)

    open fun onBackPressed(): Boolean = false
}