package com.zakrodionov.protovary.app.ext

import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
import com.zakrodionov.protovary.BuildConfig

fun String?.toFullImageUrl() = "${BuildConfig.API_IMAGE_URL}$this"

fun <T> AbsDelegationAdapter<T>.setData(data: T) {
    items = data
    notifyDataSetChanged()
}
