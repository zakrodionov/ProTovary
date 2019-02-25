package com.zakrodionov.roskachestvo.app.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.ui.NavigationUI
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.viewModel
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import kotlinx.android.synthetic.main.view_main_fragment.*

class HomeFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_main_fragment
    override fun navigationLayoutId() = R.id.hostHomeFragment

    private lateinit var homeViewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        homeViewModel = viewModel(viewModelFactory) {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()


    }

    private fun setupNavigation() {

        NavigationUI.setupWithNavController(bottomNavigation, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.mainFragment -> showBottomNavBar()
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