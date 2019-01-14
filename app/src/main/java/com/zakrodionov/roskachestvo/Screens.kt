package com.zakrodionov.roskachestvo

import com.zakrodionov.roskachestvo.app.barcode.BarcodeFragment
import com.zakrodionov.roskachestvo.app.favorites.FavoritesFragment
import com.zakrodionov.roskachestvo.app.main.MainFragment
import com.zakrodionov.roskachestvo.app.research.ResearchFragment
import com.zakrodionov.roskachestvo.app.search.SearchFragment
import com.zakrodionov.roskachestvo.common.SupportXAppScreen

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