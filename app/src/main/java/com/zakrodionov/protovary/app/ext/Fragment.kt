package com.zakrodionov.protovary.app.ext

import android.content.Context
import androidx.core.os.bundleOf
import androidx.fragment.app.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import com.zakrodionov.protovary.app.platform.BaseFragment

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) =
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

val BaseFragment.appContext: Context get() = activity?.applicationContext!!

fun Fragment.tryOpenLink(link: String?, basePath: String? = "https://google.com/search?q=") {
    activity?.tryOpenLink(link, basePath)
}
