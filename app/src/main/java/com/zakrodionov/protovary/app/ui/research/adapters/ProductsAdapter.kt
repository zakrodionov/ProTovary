package com.zakrodionov.protovary.app.ui.research.adapters

import android.view.View
import android.view.ViewGroup
import androidx.core.text.parseAsHtml
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.customSizeCropPlaceholder
import com.zakrodionov.protovary.app.ext.inflate
import com.zakrodionov.protovary.app.ext.loadFromUrl
import com.zakrodionov.protovary.app.ext.toggleVisibility
import com.zakrodionov.protovary.app.ui.view.BaseViewHolder
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.android.synthetic.main.item_product.view.*

class ProductsAdapter(
    private val clickListener: (Product) -> Unit,
    private val clickFavoriteListener: (Product) -> Unit
) : Adapter<ProductsAdapter.ViewHolder>() {

    private val items = mutableListOf<Product>()

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int) = items[position].id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_product))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(items[position])

    override fun getItemCount() = items.size

    inner class ViewHolder(containerView: View) : BaseViewHolder<Product>(containerView) {

        init {
            containerView.setOnClickListener { clickListener(item) }
            containerView.ivActionFavorite.setOnClickListener { clickFavoriteListener(item) }
        }

        override fun bind(item: Product) {
            super.bind(item)

            with(containerView) {
                tvName.text = item.name.parseAsHtml()
                ratingBar.rating = item.points.toFloat()
                tvPoints.text = item.points.toString()
                tvTrademark.text = item.trademark
                ivStatus.setImageResource(item.statusDrawable)
                ivActionFavorite.setImageResource(item.favoriteDrawable)
                tvTrademark.toggleVisibility(item.name != item.trademark)

                ivImage.loadFromUrl(item.fullImageUrl) { customSizeCropPlaceholder(context) }
            }
        }
    }

    fun updateItems(newItems: List<Product>?) {
        val diffCallback = ProductsDiffCallback(items, newItems.orEmpty())
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items.clear()
        items.addAll(newItems.orEmpty())
        diffResult.dispatchUpdatesTo(this)
    }

}