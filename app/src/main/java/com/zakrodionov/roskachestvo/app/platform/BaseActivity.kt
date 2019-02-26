package com.zakrodionov.roskachestvo.app.platform

import android.app.Activity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation

abstract class BaseActivity : AppCompatActivity() {

    abstract fun navigationLayoutId(): Int
    abstract fun snackHolderContainer(): View
    abstract fun fragmentContainer(): View

    protected val navController: NavController by lazy {
        Navigation.findNavController(this, navigationLayoutId())
    }
}
