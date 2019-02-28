package com.zakrodionov.roskachestvo.app.ui.researches

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.zakrodionov.roskachestvo.BuildConfig
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.inflate
import com.zakrodionov.roskachestvo.app.ext.loadFromUrl
import com.zakrodionov.roskachestvo.app.ui.view.BaseViewHolder
import com.zakrodionov.roskachestvo.domain.entity.ResearchCompact
import kotlinx.android.synthetic.main.item_researches.view.*
import javax.inject.Inject

class ResearchesAdapter
@Inject constructor() : Adapter<ResearchesAdapter.ViewHolder>() {

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


    inner class ViewHolder(itemView: View) : BaseViewHolder<ResearchCompact>(itemView) {

        init {
            itemView.setOnClickListener { clickListener(item) }
        }

        override fun bind(item: ResearchCompact) {
            super.bind(item)

            val url = "${BuildConfig.API_ENDPOINT.substringBeforeLast("api/")}${item.image?.src}"
            itemView.ivImage.loadFromUrl(url)
            itemView.tvName.text = item.name
        }
    }

}
