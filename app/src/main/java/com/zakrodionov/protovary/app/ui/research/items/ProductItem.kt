package com.zakrodionov.protovary.app.ui.research.items

import android.view.View
import androidx.core.text.parseAsHtml
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.context
import com.zakrodionov.protovary.app.ext.customSizeCropPlaceholder
import com.zakrodionov.protovary.app.ext.loadFromUrl
import com.zakrodionov.protovary.app.ext.toggleVisibility
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.android.synthetic.main.item_product.*

data class ProductItem(
    private val product: Product,
    private val clickListener: (Product) -> Unit,
    private val favoriteClickListener: (Product) -> Unit
) : Item() {

    override fun getId() = product.id

    override fun getLayout(): Int {
        return R.layout.item_product
    }

    override fun createViewHolder(itemView: View): GroupieViewHolder {
        return GroupieViewHolder(itemView).apply {
            containerView.setOnClickListener {
                clickListener.invoke(product)
            }

            ivActionFavorite.setOnClickListener {
                favoriteClickListener.invoke(product)
            }
        }
    }

    override fun bind(vh: GroupieViewHolder, position: Int) {
        with(vh) {
            tvName.text = product.name.parseAsHtml()
            ratingBar.rating = product.points.toFloat()
            tvPoints.text = product.points.toString()
            tvTrademark.text = product.trademark
            ivStatus.setImageResource(product.statusDrawable)
            ivActionFavorite.setImageResource(product.favoriteDrawable)
            tvTrademark.toggleVisibility(product.name != product.trademark)

            ivImage.loadFromUrl(product.fullImageUrl) { customSizeCropPlaceholder(context) }
        }
    }
}
