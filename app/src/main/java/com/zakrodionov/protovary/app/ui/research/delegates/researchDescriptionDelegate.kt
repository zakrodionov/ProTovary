package com.zakrodionov.protovary.app.ui.research.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.tryOpenLink
import com.zakrodionov.protovary.app.platform.DiffItem
import kotlinx.android.synthetic.main.item_research.*
import kotlinx.android.synthetic.main.item_research_desc.*
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter

fun researchDescriptionDelegate() =
    adapterDelegateLayoutContainer<ResearchDescriptionItem, DiffItem>(R.layout.item_research) {

        with(expandableLayout) {
            setOnClickListener {
                if (isExpanded) collapse() else expand()
            }
        }

        val htmlHttpImageGetter = HtmlHttpImageGetter(tvDesc)
        tvDesc.setOnClickATagListener { _, href ->
            context.tryOpenLink("${getString(R.string.base_url)}$href")
        }

        bind {
            tvDesc.setHtml(item.desc, htmlHttpImageGetter)
        }
    }

data class ResearchDescriptionItem(val desc: String) : DiffItem {
    override val itemId = desc
    override val itemHash = hashCode()
}
