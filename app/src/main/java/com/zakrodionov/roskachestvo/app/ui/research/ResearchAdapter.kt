package com.zakrodionov.roskachestvo.app.ui.research

import android.text.Html
import android.text.Html.FROM_HTML_MODE_LEGACY
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.zakrodionov.roskachestvo.BuildConfig
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.inflate
import com.zakrodionov.roskachestvo.app.ext.loadFromUrl
import com.zakrodionov.roskachestvo.app.ui.view.BaseViewHolder
import com.zakrodionov.roskachestvo.domain.entity.ProductsInfo
import com.zakrodionov.roskachestvo.domain.entity.Research
import com.zakrodionov.roskachestvo.domain.entity.ResearchCompact
import kotlinx.android.synthetic.main.item_researches.view.*
import javax.inject.Inject

class ResearchAdapter
@Inject constructor() : Adapter<ResearchAdapter.ViewHolder>() {

    var collection: List<ProductsInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    internal var clickListener: (ProductsInfo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_researches))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size


    inner class ViewHolder(itemView: View) : BaseViewHolder<ProductsInfo>(itemView) {

        init {
            itemView.setOnClickListener { clickListener(item) }
        }

        override fun bind(item: ProductsInfo) {
            super.bind(item)

            val url = "${BuildConfig.API_ENDPOINT.substringBeforeLast("api/")}${item.image?.src}"
            itemView.ivImage.loadFromUrl(url)
            itemView.tvName.text = Html.fromHtml(item.name)
        }
    }

}
