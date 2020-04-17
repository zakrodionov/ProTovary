package com.zakrodionov.protovary.app.ext

import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

val GroupieViewHolder.context get() = containerView.context

fun GroupieViewHolder.getString(@StringRes resId: Int): String = context.resources.getString(resId)

fun GroupieViewHolder.getString(@StringRes resId: Int, vararg formatArgs: Any?): String =
    context.resources.getString(resId, *formatArgs)

fun RecyclerView.safeAttachAdapter(groupAdapter: GroupAdapter<GroupieViewHolder>, vararg group: Group) {
    groupAdapter.clear()
    groupAdapter.addAll(listOf(*group))
    adapter = groupAdapter
}