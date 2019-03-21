package com.zakrodionov.protovary.app.ui.researchescategory

import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.inflate
import com.zakrodionov.protovary.app.ui.view.BaseViewHolder
import com.zakrodionov.protovary.domain.entity.Researches
import kotlinx.android.synthetic.main.item_researches.view.*
import javax.inject.Inject

class ResearchesCategoryAdapter
@Inject constructor() : Adapter<ResearchesCategoryAdapter.ViewHolder>() {

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


    inner class ViewHolder(itemView: View) : BaseViewHolder<Researches>(itemView) {

        init {
            itemView.setOnClickListener { clickListener(item) }
        }

        override fun bind(item: Researches) {
            super.bind(item)

            val url = "${BuildConfig.API_ENDPOINT.substringBeforeLast("api/")}${item.image?.src}"

            Glide.with(itemView.context).load(url)
                .placeholder(ContextCompat.getDrawable(itemView.context, R.drawable.ic_grey))
                .apply(RequestOptions().override(750, 500)).optionalCenterCrop().into(itemView.ivImage)

            itemView.tvName.text = item.name?.trim()
        }
    }

}
