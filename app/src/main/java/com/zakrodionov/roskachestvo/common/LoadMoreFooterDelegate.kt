package com.zakrodionov.roskachestvo.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.zakrodionov.roskachestvo.R

class LoadMoreFooterDelegate : AdapterDelegate<Any>() {
    override fun isForViewType(items: Any, position: Int): Boolean = items is Model
    override fun onCreateViewHolder(parent: ViewGroup): androidx.recyclerview.widget.RecyclerView.ViewHolder =
        object : androidx.recyclerview.widget.RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_load_more_footer, parent, false)
        ) {}

    override fun onBindViewHolder(
        items: Any,
        position: Int,
        holder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) = Unit

    open class Model
}