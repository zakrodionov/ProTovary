package com.zakrodionov.protovary.app.ui.research

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.gone
import com.zakrodionov.protovary.app.ext.inflate
import com.zakrodionov.protovary.app.ext.parseHtml
import com.zakrodionov.protovary.app.ui.view.BaseViewHolder
import com.zakrodionov.protovary.domain.entity.ProductInfo
import kotlinx.android.synthetic.main.item_product_favorite.view.*
import javax.inject.Inject

class ProductsAdapter
@Inject constructor() : Adapter<ProductsAdapter.ViewHolder>() {

    var collection: List<ProductInfo> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    internal var clickListener: (ProductInfo) -> Unit = {}
    internal var clickFavoriteListener: (ProductInfo) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_product_favorite))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size


    inner class ViewHolder(itemView: View) : BaseViewHolder<ProductInfo>(itemView) {

        init {
            itemView.setOnClickListener { clickListener(item) }
            itemView.ivActionFavorite.setOnClickListener { clickFavoriteListener(item) }
        }

        override fun bind(item: ProductInfo) {
            super.bind(item)

            val url = "${BuildConfig.API_ENDPOINT.substringBeforeLast("api/")}${item.image?.src}"

            Glide.with(itemView.context).load(url)
                .apply(RequestOptions().override(500, 450)).optionalCenterCrop().into(itemView.ivImage)

            itemView.tvName.text = item.name?.parseHtml()
            itemView.ratingBar.rating = item.points?.toFloat() ?: 0F
            itemView.tvPoints.text = item.points?.toString()
            itemView.tvTrademark.text = item.trademark

            when (item.status) {
                itemView.context.getString(R.string.status_sign) -> itemView.ivStatus.setImageResource(R.drawable.quality_sign)
                itemView.context.getString(R.string.status_violation) -> itemView.ivStatus.setImageResource(R.drawable.with_violation)
                else -> itemView.ivStatus.setImageResource(0)
            }

            when (item.isFavorite) {
                true -> itemView.ivActionFavorite.setImageResource(R.drawable.ic_favorite)
                false, null -> itemView.ivActionFavorite.setImageResource(R.drawable.ic_favorite_border)
            }

            if (item.trademark == item.name) {
                itemView.tvTrademark.gone()
            }
        }
    }

}
