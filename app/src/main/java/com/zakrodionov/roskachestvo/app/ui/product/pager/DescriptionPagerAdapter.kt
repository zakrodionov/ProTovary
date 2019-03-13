package com.zakrodionov.roskachestvo.app.ui.product.pager

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ui.product.pager.DescriptionFragment.Companion.newInstance
import com.zakrodionov.roskachestvo.app.ui.product.pager.DescriptionFragment.DescriptionType.*
import com.zakrodionov.roskachestvo.app.ui.product.pager.DescriptionFragment.Model
import com.zakrodionov.roskachestvo.domain.entity.Product

class DescriptionPagerAdapter(val context: Context, val product: Product, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    override fun getCount(): Int = 4

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> newInstance(Model(product, DESCRIPTION))
        1 -> newInstance(Model(product, PROPERTIES))
        2 -> newInstance(Model(product, INDICATORS))
        3 -> newInstance(Model(product, MANUFACTURER))
        else -> throw RuntimeException("Wrong adapter position $position actual items count $count")
    }

    override fun getPageTitle(position: Int): CharSequence? = when (position) {
        0 -> context.getString(R.string.description)
        1 -> context.getString(R.string.properties)
        2 -> context.getString(R.string.indicators)
        3 -> context.getString(R.string.manufacturer)
        else -> throw RuntimeException("Wrong adapter position $position actual items count $count")
    }

}