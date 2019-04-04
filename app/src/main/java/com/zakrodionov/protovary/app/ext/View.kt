package com.zakrodionov.protovary.app.ext

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.BaseTarget
import com.bumptech.glide.request.target.SizeReadyCallback
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.zakrodionov.protovary.R
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

fun RequestBuilder<Drawable>.setupCV(context: Context)
        = this.override(500, 450)
              .optionalCenterCrop()
              .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_grey))

fun RequestBuilder<Drawable>.setupCVBig(context: Context)
        = this.override(750, 500)
    .optionalCenterCrop()
    .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_grey))

fun View.toggleVisibility(visible: Boolean, visibilityWhenFalse: Int = View.GONE) =
    run { visibility = if (visible) View.VISIBLE else visibilityWhenFalse }

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
    LayoutInflater.from(context).inflate(layoutRes, this, false)

fun ImageView.loadFromUrl(url: String?) =
    GlideApp.with(this.context.applicationContext)
        .load(url)
        .override(900, 600)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)

fun ImageView.loadUrlAndPostponeEnterTransition(url: String, activity: androidx.fragment.app.FragmentActivity) {
    val target: Target<Drawable> = ImageViewBaseTarget(this, activity)
    Glide.with(context.applicationContext).load(url).into(target)
}

private class ImageViewBaseTarget(var imageView: ImageView?, var activity: androidx.fragment.app.FragmentActivity?) :
    BaseTarget<Drawable>() {
    override fun removeCallback(cb: SizeReadyCallback) {
        imageView = null
        activity = null
    }

    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        imageView?.setImageDrawable(resource)
        activity?.supportStartPostponedEnterTransition()
    }

    override fun onLoadFailed(errorDrawable: Drawable?) {
        super.onLoadFailed(errorDrawable)
        activity?.supportStartPostponedEnterTransition()
    }

    override fun getSize(cb: SizeReadyCallback) = cb.onSizeReady(SIZE_ORIGINAL, SIZE_ORIGINAL)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}
