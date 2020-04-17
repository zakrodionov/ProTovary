package com.zakrodionov.protovary.app.ui.research.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.inflate
import com.zakrodionov.protovary.app.ext.tryOpenLink
import com.zakrodionov.protovary.app.ui.view.BaseViewHolder
import kotlinx.android.synthetic.main.item_research.*
import kotlinx.android.synthetic.main.item_research_desc.*
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import kotlin.random.Random

class DescriptionHeaderAdapter : Adapter<DescriptionHeaderAdapter.ViewHolder>() {

    companion object {
        val id = Random.nextLong()
    }

    init {
        setHasStableIds(true)
    }

    val items = mutableListOf<String>()

    fun setItems(newItems: List<String>?) {
        items.clear()
        items.addAll(newItems.orEmpty())
        notifyDataSetChanged()
    }

    override fun getItemId(position: Int) = id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_research))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(items[position])

    override fun getItemCount() = items.size

    inner class ViewHolder(containerView: View) : BaseViewHolder<String>(containerView) {

        init {
            with(expandableLayout) {
                setOnClickListener {
                    if (isExpanded) collapse() else expand()
                }
            }

            tvDesc.setOnClickATagListener { _, href ->
                context.tryOpenLink("${getString(R.string.base_url)}$href")
            }

        }

        override fun bind(item: String) {
            super.bind(item)
            tvDesc.setHtml(item, HtmlHttpImageGetter(tvDesc))
        }
    }
}