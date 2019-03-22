package com.zakrodionov.protovary.app.ui.research

import androidx.recyclerview.widget.DiffUtil
import com.zakrodionov.protovary.domain.entity.ProductInfo

class ProductsDiffCallback(
    private val oldList: List<ProductInfo>,
    private val newList: List<ProductInfo>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].hashCode() == newList[newItemPosition].hashCode()
    }

}