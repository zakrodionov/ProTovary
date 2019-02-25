package com.zakrodionov.roskachestvo.app.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.viewModel
import com.zakrodionov.roskachestvo.app.platform.BaseFragment
import com.zakrodionov.roskachestvo.app.ui.favorites.FavoritesViewModel
import kotlinx.android.synthetic.main.view_main_fragment.*

class MainFragment : BaseFragment() {

    override fun layoutId() = R.layout.view_main_fragment

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

//    protected val navController: NavController by lazy {
//        Navigation.findNavController(navigation_fragments)
//    }

    private lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

        mainViewModel = viewModel(viewModelFactory) {

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNavigation()
    }

    private fun setupNavigation(){

        val navController = Navigation.findNavController(view!!)

        NavigationUI.setupWithNavController(bottomNavigation, navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                 R.id.mainFragment -> showBottomNavBar()
                 R.id.barcodeFragment -> showBottomNavBar()
                 R.id.favoritesFragment  -> showBottomNavBar()
                 R.id.researchFragment ->  showBottomNavBar()
                 R.id.searchFragment-> showBottomNavBar()
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