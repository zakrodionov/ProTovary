package com.zakrodionov.protovary.app.ext

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.util.Log
import android.webkit.URLUtil
import com.zakrodionov.protovary.BuildConfig
import org.jetbrains.anko.toast

val Context.networkInfo: NetworkInfo?
    get() = (this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo

fun Context.tryOpenLink(link: String?, basePath: String? = "https://google.com/search?q=") {
    if (link != null) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, when {
                        URLUtil.isValidUrl(link) -> Uri.parse(link)
                        else -> Uri.parse(basePath + link)
                    }
                )
            )
        } catch (e: Exception) {
            Log.e("error Url", "tryOpenLink error: $e")
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("https://google.com/search?q=$link")
                )
            )
        }
    }
}

fun Context.openAppLink(appPackage: String, url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setPackage(appPackage)
        startActivity(intent)
    } catch (e: Exception) {
        tryOpenLink(url)
    }
}

fun Context.openPlayMarket(
    pmPackage: String,
    errorMessage: String? = null,
    linkBrowser: String? = null
) {
    val uri = Uri.parse(pmPackage + BuildConfig.APPLICATION_ID)
    var intent = Intent(Intent.ACTION_VIEW, uri)
    intent.addFlags(
        Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    )

    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    } else {
        intent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(linkBrowser + BuildConfig.APPLICATION_ID)
        )
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        } else {
            errorMessage?.let { toast(it) }
        }
    }
}

fun Context.shareText(text: String, title: String? = null) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_TEXT, text)
    startActivity(Intent.createChooser(intent, title))
}
