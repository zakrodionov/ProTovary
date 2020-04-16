package com.zakrodionov.protovary.app.platform

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.*
import com.zakrodionov.protovary.app.platform.DiffItem

object DiffCallback : ItemCallback<DiffItem>() {

    override fun areItemsTheSame(oldItem: DiffItem, newItem: DiffItem): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: DiffItem, newItem: DiffItem): Boolean {
        return oldItem.itemHash == newItem.itemHash
    }
}