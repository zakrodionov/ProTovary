package com.zakrodionov.roskachestvo.app.ui

import android.os.Bundle
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.platform.BaseActivity
import com.zakrodionov.roskachestvo.app.platform.BaseFragment

class AppActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}