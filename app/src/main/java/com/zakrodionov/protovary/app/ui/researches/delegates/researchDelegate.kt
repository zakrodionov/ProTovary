package com.zakrodionov.protovary.app.ui.researches.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.customBigSizeCropPlaceholder
import com.zakrodionov.protovary.app.ext.loadFromUrl
import com.zakrodionov.protovary.app.ext.toFullImageUrl
import com.zakrodionov.protovary.app.platform.DisplayableItem
import com.zakrodionov.protovary.data.entity.ResearchCompact
import kotlinx.android.synthetic.main.item_researches.*

fun researchDelegate(clickListener: (ResearchCompact) -> Unit) =
    adapterDelegateLayoutContainer<ResearchCompact, DisplayableItem>(R.layout.item_researches) {

        containerView.setOnClickListener {
            clickListener.invoke(item)
        }

        bind {
            tvName.text = item.name?.trim()

            val url = item.image?.src?.toFullImageUrl()
            ivImage.loadFromUrl(url) { customBigSizeCropPlaceholder(context) }
        }
    }
