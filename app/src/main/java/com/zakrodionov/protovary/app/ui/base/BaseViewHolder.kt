package com.zakrodionov.protovary.app.ui.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import com.zakrodionov.protovary.app.ext.ContextAware

open class BaseViewHolder<T : Any>(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView),
    ContextAware {
    lateinit var model: T

    open fun bind(item: T) {
        this.model = item
    }

    override fun getContext(): Context = itemView.context

    val layoutInflater
        get() = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
}