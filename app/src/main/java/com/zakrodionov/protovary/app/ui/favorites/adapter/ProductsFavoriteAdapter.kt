package com.zakrodionov.protovary.app.ui.favorites.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.di.GlideApp
import com.zakrodionov.protovary.app.ext.customSizeCropPlaceholder
import com.zakrodionov.protovary.app.ext.inflate
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

    inner class ViewHolder(containerView: View) : BaseViewHolder<Product>(containerView) {

        init {
            containerView.setOnClickListener { clickListener(item) }
            containerView.ivActionFavorite.setOnClickListener { actionFavoriteListener(item) }
        }

        override fun bind(item: Product) {
            super.bind(item)

            with(containerView) {
                tvName.text = item.name.parseAsHtml()
                ratingBar.rating = item.points.toFloat()
                tvPoints.text = item.points.toString()
                tvTrademark.text = item.trademark
                ivStatus.setImageResource(item.statusDrawable)
                tvTrademark.toggleVisibility(item.name != item.trademark)

                GlideApp.with(context).load(item.fullImageUrl).customSizeCropPlaceholder(context).into(ivImage)
            }
        }
    }
}
