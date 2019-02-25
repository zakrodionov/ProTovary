package com.zakrodionov.roskachestvo.app.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class AppActivity : BaseActivity() {

    override fun fragmentContainer() = flFragmentContainer

    protected val navController2: NavController by lazy {
        Navigation.findNavController(this, R.id.hostAppActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navController2.addOnDestinationChangedListener { _, destination, _ ->
            when (destination) {

            }
        }
    }

}