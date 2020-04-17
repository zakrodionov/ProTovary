package com.zakrodionov.protovary.app.ui.research.delegates

import android.widget.ImageView
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import com.zakrodionov.protovary.R
import kotlinx.android.synthetic.main.item_research_title.*

object ExpandableHeaderItem : Item(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    override fun getId() = hashCode().toLong()

    override fun getLayout(): Int {
        return R.layout.item_research_title
    }

    override fun bind(vh: GroupieViewHolder, position: Int) {
        vh.tvTitle.setOnClickListener {
            expandableGroup.onToggleExpanded()
            bindIcon(vh.icon, true)
        }

        bindIcon(vh.icon)
    }

    private fun bindIcon(icon: ImageView, animated: Boolean = false) {
        if (animated) {
            if (expandableGroup.isExpanded) {
                icon.animate().rotationBy(180f).setDuration(300).start()
            } else {
                icon.animate().rotationBy(-180f).setDuration(300).start()
            }
        } else {
            icon.setImageResource(if (expandableGroup.isExpanded) R.drawable.ic_arrow_up_white else R.drawable.ic_arrow_down_white)
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        this.expandableGroup = onToggleListener
    }
}