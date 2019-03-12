package com.zakrodionov.roskachestvo.app.ext

import android.os.Build
import android.text.Html
import android.text.Spanned

fun String.parseHtml(): Spanned? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}