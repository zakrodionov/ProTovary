package com.zakrodionov.protovary.app.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.model.*
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class AppActivity : BaseActivity() {

    companion object {
        const val UPDATE_IMMEDIATE_REQUEST_CODE = 11111
        const val UPDATE_FLEXIBLE_REQUEST_CODE = 22222
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigationView()

        checkForUpdates(true)
    }

    private fun setupBottomNavigationView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.navController)
    }

    override fun onResume() {
        super.onResume()
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            when (appUpdateInfo.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                        if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                            showSnackbar(
                                getString(R.string.update_download_finished),
                                getString(R.string.update_restart)
                            ) { appUpdateManager.completeUpdate() }
                        }
                    }
                }
                UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                    if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                        appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo, AppUpdateType.IMMEDIATE, this, UPDATE_IMMEDIATE_REQUEST_CODE
                        )
                    }
                }
            }
        }
    }

    private fun checkForUpdates(userTriggered: Boolean = false) {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            when (appUpdateInfo.updateAvailability()) {
                UpdateAvailability.UPDATE_AVAILABLE -> {
                    when {
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE) -> {
                            appUpdateManager.startUpdateFlowForResult(
                                appUpdateInfo, AppUpdateType.IMMEDIATE, this, UPDATE_IMMEDIATE_REQUEST_CODE
                            )
                        }
                        appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE) -> {
                            val listener = { state: InstallState ->
                                // Show module progress, log state, or install the update.
                                when (state.installStatus()) {
                                    InstallStatus.DOWNLOADED -> {
                                        // After the update is downloaded, show a notification
                                        // and request user confirmation to restart the app.
                                        showSnackbar(
                                            getString(R.string.update_download_finished),
                                            getString(R.string.update_restart)
                                        ) {
                                            appUpdateManager.completeUpdate()
                                        }
                                    }
                                    InstallStatus.FAILED -> {
                                        showSnackbar(
                                            getString(R.string.update_download_failed),
                                            getString(R.string.update_retry)
                                        ) {
                                            checkForUpdates()
                                        }
                                    }
                                }
                            }

                            // Before starting an update, register a listener for updates.
                            appUpdateManager.registerListener(listener)

                            // Start an update.
                            appUpdateManager.startUpdateFlowForResult(
                                // Pass the intent that is returned by 'getAppUpdateInfo()'.
                                appUpdateInfo,
                                // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                                AppUpdateType.IMMEDIATE,
                                // The current activity making the update request.
                                this,
                                // Include a request code to later monitor this update request.
                                UPDATE_IMMEDIATE_REQUEST_CODE
                            )

                            // When status updates are no longer needed, unregister the listener.
                            appUpdateManager.unregisterListener(listener)
                        }
                    }
                }
                UpdateAvailability.UPDATE_NOT_AVAILABLE -> {
                    MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.update_notavailable_title))
                        .setMessage(getString(R.string.update_notavailable_message))
                        .setPositiveButton(getString(R.string.update_notavailable_ok), null)
                        .show()
                }
                UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS -> {
                    MaterialAlertDialogBuilder(this)
                        .setTitle(getString(R.string.update_inprogress_title))
                        .setMessage(getString(R.string.update_inprogress_message))
                        .setPositiveButton(getString(R.string.update_inprogress_ok), null)
                        .show()
                }
                UpdateAvailability.UNKNOWN -> {
                    if (userTriggered) {
                        MaterialAlertDialogBuilder(this)
                            .setTitle(getString(R.string.update_unknown_title))
                            .setMessage(getString(R.string.update_unknown_message))
                            .setPositiveButton(getString(R.string.update_unknown_ok), null)
                            .show()
                    }
                }
            }
        }

        appUpdateInfoTask.addOnFailureListener {
            showSnackbar(it?.message ?: "")
        }
    }

    private fun showSnackbar(
        message: String,
        actionText: String? = null,
        action: (() -> Unit)? = null
    ) {
        val snackbar: Snackbar = Snackbar.make(flFragmentContainer, message, Snackbar.LENGTH_LONG)
        action?.let {
            snackbar.setAction(actionText) { view -> action.invoke() }
            snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        }
        snackbar.show()
    }
}
