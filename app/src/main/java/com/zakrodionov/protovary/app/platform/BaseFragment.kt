package com.zakrodionov.protovary.app.platform

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.appContext
import kotlinx.android.synthetic.main.progress_layout.view.*

/**
 * Base Fragment class with helper methods for handling views and back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    private var snackBar: Snackbar? = null

    protected val navController
        get() = findNavController()

    internal fun Bundle?.isFirstTimeCreated() = this == null

    internal fun back() = navController.popBackStack()

    internal fun handleState(state: State?) {
        when (state) {
            is State.Loading -> {
                progressStatus(View.VISIBLE, View.VISIBLE); snackBar?.dismiss()
            }
            is State.Loaded -> {
                progressStatus(View.GONE, View.GONE); snackBar?.dismiss()
            }
            is State.Error -> {
                progressStatus(View.VISIBLE, View.GONE); snackBar?.dismiss()
                handleFailure(state.failure)
            }
        }
    }

    private fun progressStatus(viewStatusLayout: Int, viewStatusProgress: Int) =
        view?.let {
            it.progressLayout?.visibility = viewStatusLayout
            // it.progressLayout?.progressBar?.visibility = viewStatusProgress
        }


    internal fun notify(@StringRes message: Int) {
        view?.let {
            snackBar = Snackbar.make(it, message, Snackbar.LENGTH_SHORT)
            snackBar!!.show()
        }
    }

    internal fun notifyWithAction(
        @StringRes message: Int,
        @StringRes actionText: Int,
        action: () -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        view?.let {
            snackBar = Snackbar.make(it, message, length)
            snackBar!!.setAction(actionText) { action.invoke() }
            snackBar!!.setActionTextColor(ContextCompat.getColor(appContext, R.color.silver))
            snackBar!!.show()
        }

    }

    override fun onStop() {
        super.onStop()
        hideSoftKeyboard()
    }


    private fun showSoftKeyboard() {
        view?.let {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideSoftKeyboard() {
        view?.let {
            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    protected fun postDelayed(delayMillis: Long, r: () -> Unit) = Handler().postDelayed(r, delayMillis)

    //Если необходимо переопределяем в наследнике
    open fun loadData() {}

    open fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.ServerError -> notify(R.string.failure_server_error)
            is Failure.UnknownError -> notify(R.string.failure_unknown_error)
            is Failure.NetworkConnection -> notifyWithAction(
                R.string.failure_network_connection,
                R.string.action_refresh,
                ::loadData
            )
        }
    }
}
