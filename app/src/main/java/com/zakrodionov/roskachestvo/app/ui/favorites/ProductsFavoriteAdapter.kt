package com.zakrodionov.roskachestvo.app.ui.favorites

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zakrodionov.roskachestvo.BuildConfig
import com.zakrodionov.roskachestvo.R
import com.zakrodionov.roskachestvo.app.ext.gone
import com.zakrodionov.roskachestvo.app.ext.inflate
import com.zakrodionov.roskachestvo.app.ext.loadFromUrl
import com.zakrodionov.roskachestvo.app.ext.parseHtml
import com.zakrodionov.roskachestvo.app.ui.view.BaseViewHolder
import com.zakrodionov.roskachestvo.data.db.entity.FavoriteProduct
import kotlinx.android.synthetic.main.item_product_favorite.view.*
import javax.inject.Inject

class ProductsFavoriteAdapter
@Inject constructor() : Adapter<ProductsFavoriteAdapter.ViewHolder>() {

    var collection: List<FavoriteProduct> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    internal var clickListener: (FavoriteProduct) -> Unit = {}
    internal var actionFavoriteListener: (FavoriteProduct) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_product_favorite))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size


    inner class ViewHolder(itemView: View) : BaseViewHolder<FavoriteProduct>(itemView) {

        init {
            itemView.setOnClickListener { clickListener(item) }
            itemView.ivActionFavorite.setOnClickListener { actionFavoriteListener(item) }
        }

        override fun bind(item: FavoriteProduct) {
            super.bind(item)

            val url = "${BuildConfig.API_ENDPOINT.substringBeforeLast("api/")}${item.urlImage}"

            Glide.with(itemView.context).load(url)
                .apply(RequestOptions().override(500,450)).optionalCenterCrop().into(itemView.ivImage)

            itemView.tvName.text = item.name.parseHtml()
            itemView.ratingBar.rating = item.points.toFloat()
            itemView.tvPoints.text = item.points.toString()
            itemView.tvTrademark.text = item.trademark

            if (item.trademark == item.name) {
                itemView.tvTrademark.gone()
            }
        }
    }

}
