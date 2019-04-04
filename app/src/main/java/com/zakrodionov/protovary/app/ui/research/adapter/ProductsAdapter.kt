package com.zakrodionov.protovary.app.ui.research.adapter

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.di.GlideApp
import com.zakrodionov.protovary.app.ext.*
import com.zakrodionov.protovary.app.ui.view.BaseViewHolder
import com.zakrodionov.protovary.data.entity.ProductInfo
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.android.synthetic.main.item_product.view.*
import javax.inject.Inject

class ProductsAdapter
@Inject constructor() : Adapter<ProductsAdapter.ViewHolder>() {

    var collection: MutableList<Product> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    internal var clickListener: (Product) -> Unit = {}
    internal var clickFavoriteListener: (Product) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_product))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size


    inner class ViewHolder(itemView: View) : BaseViewHolder<Product>(itemView) {

        init {
            itemView.setOnClickListener { clickListener(item) }
            itemView.ivActionFavorite.setOnClickListener { clickFavoriteListener(item) }
        }

        override fun bind(item: Product) {
            super.bind(item)

            itemView.tvName.text = item.name.parseHtml()
            itemView.ratingBar.rating = item.points.toFloat()
            itemView.tvPoints.text = item.points.toString()
            itemView.tvTrademark.text = item.trademark
            itemView.ivStatus.setImageResource(item.statusDrawable)
            itemView.ivActionFavorite.setImageResource(item.favoriteDrawable)
            itemView.tvTrademark.toggleVisibility(item.name != item.trademark)


            GlideApp.with(itemView.context).load(item.fullImageUrl).setupCV().into(itemView.ivImage)
        }
    }

    fun updateUsingDiffUtil(products: List<Product>) {
        val diffCallback = ProductsDiffCallback(collection, products)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        collection.clear()
        collection.addAll(products)
        diffResult.dispatchUpdatesTo(this)
    }

}
