package com.zakrodionov.protovary.app.ui.view

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class BaseViewHolder<T : Any>(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    lateinit var item: T

    open fun bind(item: T) {
        this.item = item
    }
}
