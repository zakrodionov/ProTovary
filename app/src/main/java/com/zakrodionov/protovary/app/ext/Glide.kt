package com.zakrodionov.protovary.app.ext

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bumptech.glide.RequestBuilder
import com.zakrodionov.protovary.R

fun RequestBuilder<Drawable>.customSizeCropPlaceholder(context: Context) =
    override(500, 450)
        .optionalCenterCrop()
        .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_grey))

fun RequestBuilder<Drawable>.customBigSizeCropPlaceholder(context: Context) =
    override(750, 500)
        .optionalCenterCrop()
        .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_grey))
