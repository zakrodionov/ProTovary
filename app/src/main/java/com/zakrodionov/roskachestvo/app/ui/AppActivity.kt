package com.zakrodionov.roskachestvo.app.ui

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class AppActivity : BaseActivity() {

    override fun fragmentContainer() = flFragmentContainer
    override fun navigationLayoutId() = R.id.hostAppActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigation()
    }

    private fun setupNavigation() {

        NavigationUI.setupWithNavController(bottomNavigation, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.barcodeFragment -> showBottomNavBar()
                R.id.favoritesFragment -> showBottomNavBar()
                R.id.researchFragment -> showBottomNavBar()
                R.id.searchFragment -> showBottomNavBar()
                else -> hideBottomNavBar()
            }
        }
    }


    fun hideBottomNavBar() {
        bottomNavigation.visibility = View.GONE
    }

    fun showBottomNavBar() {
        bottomNavigation.visibility = View.VISIBLE
    }

}