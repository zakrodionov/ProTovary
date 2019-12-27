package com.zakrodionov.protovary.app.platform

import android.content.Context
import androidx.annotation.StringRes

class ResourceManager(val context: Context) {
    fun getString(@StringRes resId: Int): String = context.resources.getString(resId)

    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String =
        context.resources.getString(resId, *formatArgs)
}