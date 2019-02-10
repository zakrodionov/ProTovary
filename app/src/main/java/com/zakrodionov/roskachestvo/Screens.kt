package com.zakrodionov.roskachestvo

import com.zakrodionov.roskachestvo.common.SupportXAppScreen
import com.zakrodionov.roskachestvo.ui.barcode.BarcodeFragment
import com.zakrodionov.roskachestvo.ui.favorites.FavoritesFragment
import com.zakrodionov.roskachestvo.ui.main.MainFragment
import com.zakrodionov.roskachestvo.ui.research.ResearchFragment
import com.zakrodionov.roskachestvo.ui.search.SearchFragment

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