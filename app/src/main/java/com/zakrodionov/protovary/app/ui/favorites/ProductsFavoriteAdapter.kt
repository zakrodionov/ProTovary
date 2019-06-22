package com.zakrodionov.protovary.app.ui.favorites

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.di.GlideApp
import com.zakrodionov.protovary.app.ext.inflate
import com.zakrodionov.protovary.app.ext.parseHtml
import com.zakrodionov.protovary.app.ext.setupCV
import com.zakrodionov.protovary.app.ext.toggleVisibility
import com.zakrodionov.protovary.app.ui.view.BaseViewHolder
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.android.synthetic.main.item_product.view.*

class ProductsFavoriteAdapter : Adapter<ProductsFavoriteAdapter.ViewHolder>() {

    var collection: List<Product> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    internal var clickListener: (Product) -> Unit = {}
    internal var actionFavoriteListener: (Product) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_product))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size


    inner class ViewHolder(itemView: View) : BaseViewHolder<Product>(itemView) {

        init {
            itemView.setOnClickListener { clickListener(item) }
            itemView.ivActionFavorite.setOnClickListener { actionFavoriteListener(item) }
        }

        override fun bind(item: Product) {
            super.bind(item)

            itemView.tvName.text = item.name.parseHtml()
            itemView.ratingBar.rating = item.points.toFloat()
            itemView.tvPoints.text = item.points.toString()
            itemView.tvTrademark.text = item.trademark
            itemView.ivStatus.setImageResource(item.statusDrawable)
            itemView.tvTrademark.toggleVisibility(item.name != item.trademark)

            GlideApp.with(itemView.context).load(item.fullImageUrl).setupCV(itemView.context).into(itemView.ivImage)
        }
    }

}
