package com.zakrodionov.roskachestvo.app

import com.zakrodionov.roskachestvo.app.ui.base.SupportXAppScreen
import com.zakrodionov.roskachestvo.app.ui.barcode.BarcodeFragment
import com.zakrodionov.roskachestvo.app.ui.favorites.FavoritesFragment
import com.zakrodionov.roskachestvo.app.ui.main.MainFragment
import com.zakrodionov.roskachestvo.app.ui.research.ResearchFragment
import com.zakrodionov.roskachestvo.app.ui.search.SearchFragment

object Screens {
    object MainFrag : SupportXAppScreen() {
        override fun getFragment() = MainFragment()
    }

    object Barcode : SupportXAppScreen() {
        override fun getFragment() = BarcodeFragment()
    }

    object Favorites : SupportXAppScreen() {
        override fun getFragment() = FavoritesFragment()
    }

    object Search : SupportXAppScreen() {
        override fun getFragment() = SearchFragment()
    }

    object Research : SupportXAppScreen() {
        override fun getFragment() = ResearchFragment()
    }
}