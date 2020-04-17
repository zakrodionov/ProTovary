package com.zakrodionov.protovary.app.ext

import androidx.annotation.StringRes
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

val GroupieViewHolder.context get() = containerView.context

fun GroupieViewHolder.getString(@StringRes resId: Int): String = context.resources.getString(resId)

fun GroupieViewHolder.getString(@StringRes resId: Int, vararg formatArgs: Any?): String =
    context.resources.getString(resId, *formatArgs)
