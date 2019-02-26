package com.zakrodionov.roskachestvo.app.ext


import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelProviders
import com.zakrodionov.roskachestvo.app.platform.BaseActivity
import com.zakrodionov.roskachestvo.app.platform.BaseFragment

inline fun androidx.fragment.app.FragmentManager.inTransaction(func: androidx.fragment.app.FragmentTransaction.() -> androidx.fragment.app.FragmentTransaction) =
    beginTransaction().func().commit()

inline fun <reified T : ViewModel> androidx.fragment.app.Fragment.viewModel(factory: Factory, body: T.() -> Unit): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

fun BaseFragment.close() = fragmentManager?.popBackStack()

val BaseFragment.viewContainer: View get() = (activity as BaseActivity).fragmentContainer()

val BaseFragment.snackHolder: View get() = (activity as BaseActivity).snackHolderContainer()

val BaseFragment.appContext: Context get() = activity?.applicationContext!!