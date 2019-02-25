package com.zakrodionov.roskachestvo.app.ui.splash

import android.os.Bundle
import android.os.Handler
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.platform.BaseFragment

class SplashFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_splash
    override fun navigationLayoutId() = R.id.hostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({ navController.navigate(R.id.action_splashFragment_to_homeFragment) },1000)
    }
}