package com.zakrodionov.protovary.app.ui.researchescategory.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.di.GlideApp
import com.zakrodionov.protovary.app.ext.inflate
import com.zakrodionov.protovary.app.ext.setupCVBig
import com.zakrodionov.protovary.app.ui.view.BaseViewHolder
import com.zakrodionov.protovary.app.util.Utils
import com.zakrodionov.protovary.data.entity.Researches
import kotlinx.android.synthetic.main.item_researches.view.*

class ResearchesCategoryAdapter : Adapter<ResearchesCategoryAdapter.ViewHolder>() {

    var collection: List<Researches> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    internal var clickListener: (Researches) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_researches))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size

    inner class ViewHolder(containerView: View) : BaseViewHolder<Researches>(containerView) {

        init {
            containerView.setOnClickListener { clickListener(item) }
        }

        override fun bind(item: Researches) {
            super.bind(item)

            val url = "${Utils.baseImageUrl()}${item.image?.src}"

            with(containerView) {
                tvName.text = item.name?.trim()
                GlideApp.with(context).load(url).setupCVBig(context).into(ivImage)
            }

        }
    }

}
