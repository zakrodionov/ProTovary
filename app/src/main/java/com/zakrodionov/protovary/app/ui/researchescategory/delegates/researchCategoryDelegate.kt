package com.zakrodionov.protovary.app.ui.researchescategory.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.customBigSizeCropPlaceholder
import com.zakrodionov.protovary.app.ext.loadFromUrl
import com.zakrodionov.protovary.app.ext.toFullImageUrl
import com.zakrodionov.protovary.app.platform.DisplayableItem
import com.zakrodionov.protovary.data.entity.Researches
import kotlinx.android.synthetic.main.item_researches.*

fun researchCategoryDelegate(clickListener: (Researches) -> Unit) =
    adapterDelegateLayoutContainer<Researches, DisplayableItem>(R.layout.item_researches) {

        containerView.setOnClickListener {
            clickListener.invoke(item)
        }

        bind {
            tvName.text = item.name?.trim()

            val url = item.image?.src?.toFullImageUrl()
            ivImage.loadFromUrl(url) { customBigSizeCropPlaceholder(context) }
        }
    }
