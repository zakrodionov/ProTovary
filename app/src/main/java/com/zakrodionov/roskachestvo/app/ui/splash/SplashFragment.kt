package com.zakrodionov.roskachestvo.app.ui.splash

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.viewModel
import com.zakrodionov.roskachestvo.app.platform.BaseFragment

class SplashFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_splash
    override fun navigationLayoutId() = R.id.hostAppActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({ navController.navigate(R.id.action_splashFragment_to_homeFragment) },1000)
    }
}