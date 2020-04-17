package com.zakrodionov.protovary.app.ui.research.items

import android.widget.ImageView
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.rotate
import kotlinx.android.synthetic.main.item_research_title.*

object ExpandableHeaderItem : Item(), ExpandableItem {

    private lateinit var expandableGroup: ExpandableGroup

    override fun getId() = hashCode().toLong()

    override fun getLayout(): Int {
        return R.layout.item_research_title
    }

    override fun bind(vh: GroupieViewHolder, position: Int) {
        with(vh) {
            tvTitle.setOnClickListener {
                expandableGroup.onToggleExpanded()
                bindIcon(icon, true)
            }

            bindIcon(icon)
        }
    }

    private fun bindIcon(icon: ImageView, animated: Boolean = false) {
        val isExpanded = expandableGroup.isExpanded
        when {
            animated && isExpanded -> icon.rotate(-180f, 300)
            animated && !isExpanded -> icon.rotate(180f, 300)
            !animated && isExpanded -> icon.setImageResource(R.drawable.ic_arrow_up_white)
            !animated && !isExpanded -> icon.setImageResource(R.drawable.ic_arrow_down_white)
        }
    }

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }
}
