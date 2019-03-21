package com.zakrodionov.protovary.app.platform

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.App
import com.zakrodionov.protovary.app.di.ApplicationComponent
import com.zakrodionov.protovary.app.ext.appContext
import kotlinx.android.synthetic.main.progress_layout.view.*
import javax.inject.Inject

/**
 * Base Fragment class with helper methods for handling views and back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment : androidx.fragment.app.Fragment() {

    abstract fun layoutId(): Int
    abstract fun navigationLayoutId(): Int

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as App).appComponent
    }

    protected val navController: NavController by lazy {
        Navigation.findNavController(activity!!, navigationLayoutId())
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutId(), container, false)

    open fun onBackPressed() {}

    //internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    internal fun Bundle?.isFirstTimeCreated() = this == null

    internal fun loadingStatus(flag: Boolean?) {
        if (flag == true) progressStatus(View.VISIBLE) else progressStatus(View.GONE)
    }

    private fun progressStatus(viewStatus: Int) =
        view?.let { it.progressLayout?.visibility = viewStatus }

    internal fun notify(@StringRes message: Int) {
        view?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    internal fun notifyWithAction(
        @StringRes message: Int,
        @StringRes actionText: Int,
        action: () -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        view?.let {
            val snackBar = Snackbar.make(it, message, length)
            snackBar.setAction(actionText) { action.invoke() }
            snackBar.setActionTextColor(ContextCompat.getColor(appContext, R.color.silver))
            snackBar.show()
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
}
