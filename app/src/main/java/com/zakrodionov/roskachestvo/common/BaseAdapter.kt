package com.zakrodionov.roskachestvo.common

import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager

abstract class BaseAdapter<T : Any>(data: List<T>) :
    androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    protected var delegatesManager: AdapterDelegatesManager<Any> = AdapterDelegatesManager()
    var data: List<T> = data
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemViewType(position: Int): Int = delegatesManager.getItemViewType(data[position], position)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): androidx.recyclerview.widget.RecyclerView.ViewHolder = delegatesManager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) =
        delegatesManager.onBindViewHolder(data[position], position, holder)

    override fun getItemCount(): Int = data.size

    fun isItemHasOwnDivider(position: Int): Boolean = (getDelegateForPosition(position) as? HasOwnDivider) != null

    private fun getDelegateForPosition(position: Int): AdapterDelegate<Any>? =
        delegatesManager.getDelegateForViewType(delegatesManager.getItemViewType(data[position], position))
}