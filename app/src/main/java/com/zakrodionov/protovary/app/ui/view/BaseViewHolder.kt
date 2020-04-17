package com.zakrodionov.protovary.app.ui.view

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class BaseViewHolder<T : Any>(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    protected lateinit var item: T

    protected val context get() = containerView.context

    protected fun getString(@StringRes resId: Int): String = context.resources.getString(resId)

    protected fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String =
        context.resources.getString(resId, *formatArgs)

    open fun bind(item: T) {
        this.item = item
    }
}
