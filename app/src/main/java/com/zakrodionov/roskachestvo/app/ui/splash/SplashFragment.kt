package com.zakrodionov.roskachestvo.app.ui.splash

import android.os.Handler
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.platform.BaseFragment

class SplashFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_splash
    override fun navigationLayoutId() = R.id.hostFragment
    override fun failureHolderId() = R.id.failureHolder


    //Avoid "navigate() call: FragmentManager has already saved its state" when run this code in onCreate()
    override fun onResume() {
        super.onResume()

        Handler().postDelayed({
            if (navController.currentDestination?.id != R.id.researchFragment) {
                navController.navigate(R.id.action_splashFragment_to_researchFragment)
            }

        }, 1000)
    }
}