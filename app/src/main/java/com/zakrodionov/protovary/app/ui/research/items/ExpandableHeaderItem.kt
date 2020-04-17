package com.zakrodionov.protovary.app.ui.research.items

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.getString
import com.zakrodionov.protovary.app.ext.tryOpenLink
import kotlinx.android.synthetic.main.item_research.*
import kotlinx.android.synthetic.main.item_research_desc.view.*
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter

data class ExpandableHeaderItem(private val desc: String) : Item() {

    override fun getId() = hashCode().toLong()

    override fun getLayout(): Int {
        return R.layout.item_research
    }

    override fun bind(vh: GroupieViewHolder, position: Int) {
        with(vh.expandableLayout) {
            setOnClickListener {
                if (isExpanded) collapse() else expand()
            }

            tvDesc.setOnClickATagListener { _, href ->
                context.tryOpenLink("${vh.getString(R.string.base_url)}$href")
            }

            tvDesc.setHtml(desc, HtmlHttpImageGetter(tvDesc))
        }
    }
}
