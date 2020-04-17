package com.zakrodionov.protovary.app.ui.research.items

import android.view.View
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.context
import com.zakrodionov.protovary.app.ext.getString
import com.zakrodionov.protovary.app.ext.tryOpenLink
import kotlinx.android.synthetic.main.item_research_desc.*
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter

data class DescItem(
    private val desc: String
) : Item() {

    private lateinit var htmlHttpImageGetter: HtmlHttpImageGetter

    override fun getId() = hashCode().toLong()

    override fun getLayout(): Int {
        return R.layout.item_research_desc
    }

    override fun createViewHolder(itemView: View): GroupieViewHolder {
        return GroupieViewHolder(itemView).apply {
            htmlHttpImageGetter = HtmlHttpImageGetter(tvDesc)
            tvDesc.setOnClickATagListener { _, href ->
                context.tryOpenLink("${getString(R.string.base_url)}$href")
            }
        }
    }

    override fun bind(vh: GroupieViewHolder, position: Int) {
        with(vh) {
            tvDesc.setHtml(desc, htmlHttpImageGetter)
        }
    }
}
