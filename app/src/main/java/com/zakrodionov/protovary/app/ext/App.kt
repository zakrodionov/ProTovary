package com.zakrodionov.protovary.app.ext

import com.zakrodionov.protovary.BuildConfig

fun String?.toFullImageUrl() = "${BuildConfig.API_IMAGE_URL}$this"
