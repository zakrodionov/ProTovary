package com.zakrodionov.protovary.app.ui.research.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.inflate
import com.zakrodionov.protovary.app.ext.toDateFormatGmt
import com.zakrodionov.protovary.app.ext.tryOpenLink
import com.zakrodionov.protovary.app.ui.view.BaseViewHolder
import com.zakrodionov.protovary.data.entity.DescriptionHeader
import kotlinx.android.synthetic.main.item_research.*
import kotlinx.android.synthetic.main.item_research_desc.*
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import kotlin.random.Random

class DescriptionHeaderAdapter : Adapter<DescriptionHeaderAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private val items = mutableListOf<DescriptionHeader>()

    override fun getItemId(position: Int) = id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_research))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(items[position])

    override fun getItemCount() = items.size

    inner class ViewHolder(containerView: View) :
        BaseViewHolder<DescriptionHeader>(containerView) {

        init {
            with(expandableLayout) {
                setOnClickListener {
                    if (isExpanded) collapse() else expand()
                }
            }

            tvDesc.setOnClickATagListener { _, _, href ->
                context.tryOpenLink("${getString(R.string.base_url)}$href")
                true
            }
        }

        override fun bind(item: DescriptionHeader) {
            super.bind(item)
            tvDate.text = getString(R.string.research_date_template, item.date?.toDateFormatGmt())
            item.description?.let { tvDesc.setHtml(it, HtmlHttpImageGetter(tvDesc)) }
        }
    }

    fun setItems(newItems: List<DescriptionHeader>?) {
        items.clear()
        items.addAll(newItems.orEmpty())
        notifyDataSetChanged()
    }

    companion object {
        val id = Random.nextLong()
    }
}
