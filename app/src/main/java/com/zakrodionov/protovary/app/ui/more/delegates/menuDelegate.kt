package com.zakrodionov.protovary.app.ui.more.delegates

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.platform.DisplayableItem
import kotlinx.android.synthetic.main.item_menu.*

fun menuDelegate(clickListener: (MenuItem) -> Unit) =
    adapterDelegateLayoutContainer<MenuItem, DisplayableItem>(R.layout.item_menu) {

        containerView.setOnClickListener {
            clickListener.invoke(item)
        }

        bind {
            tvTitle.text = getString(item.title)
            ivIcon.setImageResource(item.icon)
        }
    }

enum class MenuItem(@StringRes val title: Int, @DrawableRes val icon: Int) : DisplayableItem {
    RATE(R.string.rate_app, R.drawable.ic_star_border),
    ABOUT(R.string.about_app, R.drawable.ic_about),
    SEARCH(R.string.research_on_site, R.drawable.ic_search_white),
    THEME(R.string.preferences_theme, R.drawable.ic_brightness),
    TELEGRAM(R.string.telegram, R.drawable.ic_telegram_logo),
}
