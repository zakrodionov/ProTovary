package com.zakrodionov.protovary.app.ui.research.delegates

import androidx.core.text.parseAsHtml
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import com.zakrodionov.protovary.R
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

    override fun bind(vh: GroupieViewHolder, position: Int) {
        with(vh) {
            containerView.setOnClickListener {
                clickListener.invoke(product)
            }

            ivActionFavorite.setOnClickListener {
                favoriteClickListener.invoke(product)
            }

            tvName.text = product.name.parseAsHtml()
            ratingBar.rating = product.points.toFloat()
            tvPoints.text = product.points.toString()
            tvTrademark.text = product.trademark
            ivStatus.setImageResource(product.statusDrawable)
            ivActionFavorite.setImageResource(product.favoriteDrawable)
            tvTrademark.toggleVisibility(product.name != product.trademark)

            val context = vh.containerView.context
            ivImage.loadFromUrl(product.fullImageUrl) { customSizeCropPlaceholder(context) }
        }
    }
}