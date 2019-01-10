package com.zakrodionov.roskachestvo.common

import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager


class BaseLoadMoreDelegateAdapter<T : Any>(data: List<T>) : androidx.recyclerview.widget.RecyclerView.Adapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>() {
    protected var delegatesManager: AdapterDelegatesManager<Any> = AdapterDelegatesManager()
    var data: List<T> = data
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var loadMoreEnabled = false
        set(value) {
            if (value == field) return
            field = value
            notifyDataSetChanged()
        }

    private val footerDelegate = LoadMoreFooterDelegate()
    private val footerModel = LoadMoreFooterDelegate.Model()

    init {
        delegatesManager.addDelegate(footerDelegate)
    }

    override fun getItemViewType(position: Int): Int {
        if (loadMoreEnabled && position == itemCount - 1) return delegatesManager.getItemViewType(footerModel, position)
        return delegatesManager.getItemViewType(data[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder = delegatesManager.onCreateViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        if (loadMoreEnabled && position == itemCount - 1) {
            delegatesManager.onBindViewHolder(footerModel, position, holder)
            return
        }
        delegatesManager.onBindViewHolder(data[position], position, holder)
    }

    override fun getItemCount(): Int = if (data.isNotEmpty() && loadMoreEnabled) data.size + 1 else data.size

    fun isItemHasOwnDivider(position: Int): Boolean = (getDelegateForPosition(position) as? HasOwnDivider) != null

    private fun getDelegateForPosition(position: Int): AdapterDelegate<Any>? = delegatesManager.getDelegateForViewType(delegatesManager.getItemViewType(data[position], position))

    class Builder<T : Any> {
        private val delegates: ArrayList<AdapterDelegate<Any>> = ArrayList()

        fun add(delegateAdapter: AdapterDelegate<Any>): Builder<T> {
            delegates.add(delegateAdapter)
            return this
        }

        fun build(data: List<T>): BaseLoadMoreDelegateAdapter<T> {
            if (delegates.isEmpty()) throw IllegalArgumentException("Register at least one adapter")

            val adapter = BaseLoadMoreDelegateAdapter(data)
            delegates.forEach { adapter.delegatesManager.addDelegate(it) }

            return adapter
        }
    }
}