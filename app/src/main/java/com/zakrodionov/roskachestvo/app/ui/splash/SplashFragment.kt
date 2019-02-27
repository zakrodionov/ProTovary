package com.zakrodionov.roskachestvo.app.ui.splash

import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.navigateTo
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_splash
    override fun navigationLayoutId() = R.id.hostFragment
    override fun failureHolderId() = R.id.failureHolder


    //Avoid "navigate() call: FragmentManager has already saved its state" when run this code in onCreate()
    override fun onResume() {
        super.onResume()

        CoroutineScope(Dispatchers.Main).launch {
            delay(1000L)
            navController.navigateTo(
                R.id.action_splashFragment_to_researchesCategoryFragment,
                R.id.researchesCategoryFragment
            )
        }
    }

}