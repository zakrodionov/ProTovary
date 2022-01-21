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

        checkForUpdates()
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

    private fun checkForUpdates() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
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

                    appUpdateManager.registerListener(listener)
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo, AppUpdateType.FLEXIBLE, this, UPDATE_FLEXIBLE_REQUEST_CODE
                    )
                    appUpdateManager.unregisterListener(listener)
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
