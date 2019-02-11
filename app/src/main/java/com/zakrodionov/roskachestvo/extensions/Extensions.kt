package com.zakrodionov.roskachestvo.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Point
import android.net.Uri
import android.text.SpannableStringBuilder
import android.util.Log
import android.view.View
import android.view.View.DRAWING_CACHE_QUALITY_HIGH
import android.view.inputmethod.InputMethodManager
import android.webkit.URLUtil
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.DimenRes
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxrelay2.Relay
import com.zakrodionov.roskachestvo.entity.RequestResult
import com.zakrodionov.roskachestvo.utils.RetryWithDelay
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.exceptions.CompositeException
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.bundleOf
import retrofit2.HttpException
import java.io.IOException
import java.math.BigDecimal
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.NumberFormat
import java.util.*
import java.util.concurrent.TimeUnit


fun Boolean.toInt() = if (this) 1 else 0

fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start

fun ClosedRange<Float>.random() = Random().nextFloat() * (endInclusive - start) + start

inline fun <reified T : androidx.fragment.app.Fragment> instanceOf(vararg params: Pair<String, Any>) =
    T::class.java.newInstance().apply {
        arguments = bundleOf(*params)
    }

fun Int.pxToDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.dpToPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

fun androidx.recyclerview.widget.RecyclerView.setItemDecoration(itemDecoration: androidx.recyclerview.widget.RecyclerView.ItemDecoration) {
    for (i in 0 until itemDecorationCount) removeItemDecorationAt(i)
    addItemDecoration(itemDecoration)
}

fun androidx.fragment.app.Fragment.closeKeyboard() {
    activity?.currentFocus?.let {
        val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun AppCompatActivity.closeKeyboard() {
    currentFocus?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun <T> Flowable<T>.retrofitResponseToResult(): Flowable<RequestResult<T>> = this.map { it.asResult() }
    .onErrorReturn { if (it is HttpException || it is IOException) return@onErrorReturn it.asErrorResult<T>() else throw it }

fun <T> Flowable<T>.retryOnError(specificErrorHandler401: (() -> Unit)? = null): Flowable<T> = this
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())
    .onErrorResumeNext { throwable: Throwable ->
        if (throwable is CompositeException) Flowable.error(throwable.exceptions[0]) else Flowable.error(
            throwable
        )
    }
    .retryWhen(RetryWithDelay(3, 1000, Function { t -> t is UnknownHostException || t is SocketTimeoutException }))
    .retryWhen(RetryWithDelay(3, 1000, Function { t ->
        if (t is HttpException && t.code() == 401) {
            specificErrorHandler401?.invoke()
            return@Function true
        }
        false
    }))

fun Completable.retryOnError(specificErrorHandler401: (() -> Unit)? = null): Completable = this
    .subscribeOn(Schedulers.io())
    .observeOn(Schedulers.io())
    .onErrorResumeNext { throwable: Throwable ->
        if (throwable is CompositeException) Completable.error(throwable.exceptions[0]) else Completable.error(
            throwable
        )
    }
    .retryWhen(RetryWithDelay(3, 1000, Function { t -> t is UnknownHostException || t is SocketTimeoutException }))
    .retryWhen(RetryWithDelay(3, 1000, Function { t ->
        if (t is HttpException && t.code() == 401) {
            specificErrorHandler401?.invoke()
            return@Function true
        }
        false
    }))

fun <T> Observable<T>.delayAction(): Observable<T> = this.throttleFirst(500, TimeUnit.MILLISECONDS)

fun <T> T.asResult(): RequestResult<T> = RequestResult.Success(this)

fun <T> Throwable.asErrorResult(): RequestResult<T> = RequestResult.Error(this)

/**
 * Method to get fragment by id. The operation is performed by the supportFragmentManager.
 */
fun AppCompatActivity.findFragmentById(@IdRes id: Int): androidx.fragment.app.Fragment? {
    return supportFragmentManager.findFragmentById(id)
}

fun androidx.fragment.app.Fragment.getDimension(@DimenRes id: Int): Float {
    return resources.getDimension(id)
}

val <T> Relay<T>.observable get() = this as Observable<T>
val <T> Relay<T>.consumer get() = this as Consumer<T>

fun SpannableStringBuilder.appendExtended(text: CharSequence, what: Any, flags: Int): SpannableStringBuilder {
    val start = length
    append(text)
    setSpan(what, start, length, flags)
    return this
}

fun SpannableStringBuilder.appendExtended(text: CharSequence, what: List<Any>, flags: Int): SpannableStringBuilder {
    val start = length
    append(text)
    what.forEach { setSpan(it, start, length, flags) }
    return this
}

fun String.toValue() = if (this == "") null else this.toInt()
fun String.toFloatValue() = if (this == "" || this == "," || this == ".") null else this.replace(',', '.').toFloat()

fun Int?.toValue() = this?.toString() ?: ""

fun Date.daysBetween(endDate: Date): Long = (this.time - endDate.time) / (24 * 60 * 60 * 1000)

fun Date.addDays(days: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DAY_OF_MONTH, days)
    return calendar.time
}

val androidx.recyclerview.widget.RecyclerView.ViewHolder.context: Context
    get() = itemView.context

fun View.invisible() = run { visibility = View.INVISIBLE }
fun View.visible() = run { visibility = View.VISIBLE }
fun View.gone() = run { visibility = View.GONE }

fun View.toggleVisibility(visible: Boolean, visibilityWhenFalse: Int = View.GONE) =
    run { visibility = if (visible) View.VISIBLE else visibilityWhenFalse }

fun View.isInvisible(): Boolean = visibility == View.INVISIBLE

fun View.isVisible(): Boolean = visibility == View.VISIBLE

fun View.isGone(): Boolean = visibility == View.GONE

fun View.disable() = run { this.isEnabled = false }
fun View.enable() = run { this.isEnabled = true }

fun View.toggleAvailability(enabled: Boolean) = run { isEnabled = enabled }

fun Float.roundZero() = NumberFormat.getInstance().format(this)

fun String.parseToFloat(): Float = if (this == "," || this == ".") 0F else this.replace(',', '.').toFloat()

fun Float.roundFloatUp(decimalPlace: Int): Float {
    var bd = BigDecimal(java.lang.Float.toString(this))
    bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP)
    return bd.toFloat()
}

//gets the screen width
fun Activity.getScreenWidth(): Int {
    val size = Point()
    windowManager.defaultDisplay.getSize(size)
    return size.x
}

//gets the screen height
fun Activity.getScreenHeight(): Int {
    val size = Point()
    windowManager.defaultDisplay.getSize(size)
    return size.y
}

//converts pixels to DP
fun Float.convertToDp(): Float {
    val metrics = Resources.getSystem().displayMetrics
    val dp = this / (metrics.densityDpi / 160f)
    return Math.round(dp).toFloat()
}

fun Float.convertToPx(): Float {
    val metrics = Resources.getSystem().displayMetrics
    val px = this * (metrics.densityDpi / 160f)
    return Math.round(px).toFloat()
}

inline fun View.delayedClicks(): Observable<Unit> = this.clicks().throttleFirst(500, TimeUnit.MILLISECONDS)

fun TextView.observeText(block: ((String) -> (Unit))? = null) =
    RxTextView.textChanges(this).map { it.toString() }.doOnNext { block?.invoke(it) }


fun androidx.recyclerview.widget.RecyclerView.initializeWithDefaults() {
    setHasFixedSize(true)
    setItemViewCacheSize(20)
    isDrawingCacheEnabled = true
    drawingCacheQuality = DRAWING_CACHE_QUALITY_HIGH
    itemAnimator = null
}

fun EditText.textForReq() = this.text.toString().replace(" ", "")

fun androidx.fragment.app.Fragment.tryOpenLink(link: String?, basePath: String? = "https://google.com/search?q=") {
    if (link != null) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    when {
                        URLUtil.isValidUrl(link) -> Uri.parse(link)
                        else -> Uri.parse(basePath + link)
                    }
                )
            )
        } catch (e: Exception) {
            Log.e("error Url", "tryOpenLink error: $e")
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://google.com/search?q=$link")
                )
            )
        }
    }
}
