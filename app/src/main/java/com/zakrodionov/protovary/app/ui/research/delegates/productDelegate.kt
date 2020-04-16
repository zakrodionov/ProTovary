package com.zakrodionov.protovary.app.ui.research.delegates

import androidx.core.text.parseAsHtml
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.customSizeCropPlaceholder
import com.zakrodionov.protovary.app.ext.loadFromUrl
import com.zakrodionov.protovary.app.ext.toggleVisibility
import com.zakrodionov.protovary.app.platform.DiffItem
import com.zakrodionov.protovary.domain.model.Product
import kotlinx.android.synthetic.main.item_product.*

fun productDelegate(
    clickListener: (Product) -> Unit,
    favoriteClickListener: (Product) -> Unit
) = adapterDelegateLayoutContainer<Product, DiffItem>(R.layout.item_product) {

    containerView.setOnClickListener {
        clickListener.invoke(item)
    }

    ivActionFavorite.setOnClickListener {
        favoriteClickListener.invoke(item)
    }

    bind {
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
