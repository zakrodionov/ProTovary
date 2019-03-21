package com.zakrodionov.protovary.app

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.setupWithNavController
import com.zakrodionov.protovary.app.platform.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class AppActivity : BaseActivity() {

    private var currentNavController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setupBottomNavigationBar()
    }


    private fun setupBottomNavigationBar() {
        val navGraphIds =
            listOf(R.navigation.researches, R.navigation.barcode, R.navigation.favorites, R.navigation.more)

        val controller = bottomNavigation.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.hostFragment,
            intent = intent
        )

//        controller.observe(this, Observer {
//            it.addOnDestinationChangedListener { _, _, _ ->
//                bottomNavigation.requestApplyInsets()
//            }
//        })
        currentNavController = controller.value
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.navigateUp() ?: false
    }

    override fun onBackPressed() {
        //bottomNavigation.requestApplyInsets()
        if (currentNavController?.popBackStack() != true) {
            super.onBackPressed()
        }
    }

}