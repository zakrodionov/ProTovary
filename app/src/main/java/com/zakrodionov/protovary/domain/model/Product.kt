package com.zakrodionov.protovary.domain.model

import androidx.annotation.DrawableRes
import com.zakrodionov.protovary.app.platform.DiffItem
import java.io.Serializable

data class Product(
    val id: Long,
    val urlImage: String,
    val name: String,
    val points: Double,
    val status: String,
    val trademark: String,
    val isFavorite: Boolean?,

    //todo
    var fullImageUrl: String,
    @DrawableRes
    var favoriteDrawable: Int,
    @DrawableRes
    var statusDrawable: Int
) : Serializable, DiffItem {
    override val itemId = id.toString()
    override val itemHash = hashCode()
}
