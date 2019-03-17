package com.zakrodionov.roskachestvo.app.ext


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import com.zakrodionov.roskachestvo.app.platform.BaseActivity
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import org.jetbrains.anko.bundleOf

inline fun androidx.fragment.app.FragmentManager.inTransaction(func: androidx.fragment.app.FragmentTransaction.() -> androidx.fragment.app.FragmentTransaction) =
    beginTransaction().func().commit()

inline fun <reified T : ViewModel> Fragment.viewModel(factory: Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

inline fun <reified T : Fragment> instanceOf(vararg params: Pair<String, Any>) =
    T::class.java.newInstance().apply {
        arguments = bundleOf(*params)
    }

fun BaseFragment.close() = fragmentManager?.popBackStack()

val BaseFragment.viewContainer: View get() = (activity as BaseActivity).fragmentContainer()

val BaseFragment.appContext: Context get() = activity?.applicationContext!!

fun Fragment.tryOpenLink(link: String?, basePath: String? = "https://google.com/search?q=") {
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
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com/search?q=$link")))
        }
    }
}