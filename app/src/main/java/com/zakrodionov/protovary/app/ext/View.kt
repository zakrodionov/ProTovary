package com.zakrodionov.protovary.app.ext

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.zakrodionov.protovary.app.di.GlideApp

fun Int.pxToDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun View.cancelTransition() {
    transitionName = null
}

fun View.isVisible() = this.visibility == View.VISIBLE

fun View.invisible() = run { visibility = View.INVISIBLE }

fun View.visible() = run { visibility = View.VISIBLE }

fun View.gone() = run { visibility = View.GONE }

fun View.toggleVisibility(visible: Boolean, visibilityWhenFalse: Int = View.GONE) =
    run { visibility = if (visible) View.VISIBLE else visibilityWhenFalse }

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.loadFromUrl(url: String?) =
    GlideApp.with(context.applicationContext)
        .load(url)
        .override(900, 600)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

fun ImageView.loadFromUrl(
    url: String?,
    customize: (RequestBuilder<Drawable>.() -> RequestBuilder<Drawable>)? = null
) =
    GlideApp.with(context.applicationContext)
        .load(url)
        .also { glideRequest ->
            if (customize != null) {
                glideRequest.customize()
            }
        }
        .into(this)

fun RecyclerView.disableChangeAnimation() {
    (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
}
