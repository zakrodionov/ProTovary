package com.zakrodionov.roskachestvo.app.ui.research

import android.text.Html
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.zakrodionov.roskachestvo.BuildConfig
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.gone
import com.zakrodionov.roskachestvo.app.ext.inflate
import com.zakrodionov.roskachestvo.app.ext.loadFromUrl
import com.zakrodionov.roskachestvo.app.ext.parseHtml
import com.zakrodionov.roskachestvo.app.ui.view.BaseViewHolder
import com.zakrodionov.roskachestvo.domain.entity.ProductInfo
import kotlinx.android.synthetic.main.item_product.view.*
import javax.inject.Inject

class ProductsAdapter
@Inject constructor() : Adapter<ProductsAdapter.ViewHolder>() {

    var collection: List<ProductInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    internal var clickListener: (ProductInfo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_product))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size


    inner class ViewHolder(itemView: View) : BaseViewHolder<ProductInfo>(itemView) {

        init {
            itemView.setOnClickListener { clickListener(item) }
        }

        override fun bind(item: ProductInfo) {
            super.bind(item)

            val url = "${BuildConfig.API_ENDPOINT.substringBeforeLast("api/")}${item.image?.src}"
            itemView.ivImage.loadFromUrl(url)
            itemView.tvName.text = item.name?.parseHtml()
            itemView.mrbStar.rating = item.points?.toFloat() ?: 0F
            itemView.tvRating.text = item.points?.toString()
            itemView.tvTrademark.text = item.trademark

            if (item.trademark == item.name) {
                itemView.tvTrademark.gone()
            }
        }
    }

}
