package com.zakrodionov.roskachestvo.app.ui.researchescategory

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.zakrodionov.roskachestvo.BuildConfig
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.inflate
import com.zakrodionov.roskachestvo.app.ext.loadFromUrl
import com.zakrodionov.roskachestvo.app.ui.view.BaseViewHolder
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory
import kotlinx.android.synthetic.main.item_researches.view.*
import javax.inject.Inject

class ResearchesCategoryAdapter
@Inject constructor() : Adapter<ResearchesCategoryAdapter.ViewHolder>() {

    var collection: List<ResearchesCategory> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    internal var clickListener: (ResearchesCategory) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_researches))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size


    inner class ViewHolder(itemView: View) : BaseViewHolder<ResearchesCategory>(itemView) {

        init {
            itemView.setOnClickListener { clickListener(item) }
        }

        override fun bind(item: ResearchesCategory) {
            super.bind(item)

            val url = "${BuildConfig.API_ENDPOINT.substringBeforeLast("api/")}${item.image?.src}"
            itemView.ivImage.loadFromUrl(url)
            itemView.tvName.text = item.name
        }
    }

}
