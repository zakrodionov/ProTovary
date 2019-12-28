package com.zakrodionov.protovary.app.ui.researches.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.di.GlideApp
import com.zakrodionov.protovary.app.ext.inflate
import com.zakrodionov.protovary.app.ext.setupCVBig
import com.zakrodionov.protovary.app.ui.view.BaseViewHolder
import com.zakrodionov.protovary.app.util.Utils.createImageUrl
import com.zakrodionov.protovary.data.entity.ResearchCompact
import kotlinx.android.synthetic.main.item_researches.view.*

class ResearchesAdapter : Adapter<ResearchesAdapter.ViewHolder>() {

    var collection: List<ResearchCompact> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    internal var clickListener: (ResearchCompact) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_researches))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size


    inner class ViewHolder(containerView: View) : BaseViewHolder<ResearchCompact>(containerView) {

        init {
            containerView.setOnClickListener { clickListener(item) }
        }

        override fun bind(item: ResearchCompact) {
            super.bind(item)

            val url = createImageUrl(item.image?.src)

            with(containerView) {
                tvName.text = item.name?.trim()
                GlideApp.with(context).load(url).setupCVBig(context).into(ivImage)
            }

        }
    }

}
