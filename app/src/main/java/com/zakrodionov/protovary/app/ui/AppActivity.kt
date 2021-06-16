package com.zakrodionov.protovary.app.ui

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class AppActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        bottomNavigationView.setupWithNavController(navHostFragment.navController)
    }
}
