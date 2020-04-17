package com.zakrodionov.protovary.app.ui.research.delegates

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.tryOpenLink
import kotlinx.android.synthetic.main.item_research_desc.*
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter

data class DescItem(
    private val desc: String
) : Item() {

    override fun getId() = hashCode().toLong()

    override fun getLayout(): Int {
        return R.layout.item_research_desc
    }

    override fun bind(vh: GroupieViewHolder, position: Int) {
        val htmlHttpImageGetter = HtmlHttpImageGetter(vh.tvDesc)
        val context = vh.containerView.context
        vh.tvDesc.setOnClickATagListener { _, href ->
            context.tryOpenLink("${context.getString(R.string.base_url)}$href")
        }

        vh.tvDesc.setHtml(desc, htmlHttpImageGetter)
    }
}