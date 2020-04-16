package com.zakrodionov.protovary.app.ui.research.adapter

import androidx.core.text.parseAsHtml
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.di.GlideApp
import com.zakrodionov.protovary.app.ext.gone
import com.zakrodionov.protovary.app.ext.setupCV
import com.zakrodionov.protovary.app.ext.toggleVisibility
import com.zakrodionov.protovary.app.ext.visible
import com.zakrodionov.protovary.app.platform.DiffItem
import com.zakrodionov.protovary.app.util.Utils.slideBottomTransition
import com.zakrodionov.protovary.app.util.Utils.slideTopTransition
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.android.synthetic.main.item_product.*
import kotlinx.android.synthetic.main.item_research.*
import kotlinx.android.synthetic.main.item_research_desc.*

fun researchDescriptionDelegate() =
    adapterDelegateLayoutContainer<ResearchDescriptionItem, DiffItem>(R.layout.item_research) {

        with(expandableLayout) {
            setOnClickListener {
                if (isExpanded) collapse() else expand()
            }
        }

        bind {
            tvDesc.text = item.desc
        }

    }

data class ResearchDescriptionItem(val desc: String) : DiffItem {
    override val itemId = desc
    override val itemHash = hashCode()
}