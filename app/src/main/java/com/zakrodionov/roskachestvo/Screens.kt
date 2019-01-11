package com.zakrodionov.roskachestvo

import com.zakrodionov.roskachestvo.app.main.MainFragment
import com.zakrodionov.roskachestvo.common.SupportXAppScreen

object Screens {
    object MainFrag : SupportXAppScreen() {
        override fun getFragment() = MainFragment()
    }
}