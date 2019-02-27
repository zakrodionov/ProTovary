package com.zakrodionov.roskachestvo.app.platform

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.AndroidApplication
import com.zakrodionov.roskachestvo.app.di.ApplicationComponent
import com.zakrodionov.roskachestvo.app.ext.appContext
import kotlinx.android.synthetic.main.progress_layout.*
import javax.inject.Inject

/**
 * Base Fragment class with helper methods for handling views and back button events.
 *
 * @see Fragment
 */
abstract class BaseFragment : androidx.fragment.app.Fragment() {

    abstract fun layoutId(): Int
    abstract fun navigationLayoutId(): Int
    abstract fun failureHolderId(): Int

    val appComponent: ApplicationComponent by lazy(mode = LazyThreadSafetyMode.NONE) {
        (activity?.application as AndroidApplication).appComponent
    }

    protected val navController: NavController by lazy {
        Navigation.findNavController(activity!!, navigationLayoutId())
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutId(), container, false)

    open fun onBackPressed() {}

    internal fun firstTimeCreated(savedInstanceState: Bundle?) = savedInstanceState == null

    internal fun loadingStatus(flag: Boolean?) =
        if (flag == true) progressStatus(View.VISIBLE) else progressStatus(View.GONE)

    private fun progressStatus(viewStatus: Int) =
        with(activity) { if (this is BaseActivity) this.progressLayout?.visibility = viewStatus }

    internal fun notify(@StringRes message: Int) {
        snackHolder()?.let {
            Snackbar.make(it, message, Snackbar.LENGTH_SHORT).show()
        }
    }

    internal fun notifyWithAction(
        @StringRes message: Int, @StringRes actionText: Int, action: () -> Unit,
        length: Int = Snackbar.LENGTH_INDEFINITE
    ) {
        snackHolder()?.let {
            val snackBar = Snackbar.make(it, message, length)
            snackBar.setAction(actionText) { _ -> action.invoke() }
            snackBar.setActionTextColor(ContextCompat.getColor(appContext, R.color.silver))
            snackBar.show()
        }

    }

    fun snackHolder() = activity?.findViewById<CoordinatorLayout>(failureHolderId())

}
