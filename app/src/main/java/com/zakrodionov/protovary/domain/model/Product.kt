package com.zakrodionov.protovary.domain.model

import android.content.Context
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.ext.toFullImageUrl
import com.zakrodionov.protovary.app.platform.DiffItem
import java.io.Serializable

data class Product(
    val id: Long,
    val urlImage: String,
    val name: String,
    val points: Double,
    val status: String,
    val trademark: String,
    val isFavorite: Boolean?
) : Serializable, DiffItem {

    companion object {
        const val STATUS_SIGN = "withsign"
        const val STATUS_VIOLATION = "withviolation"
    }

    override val itemId = id.toString()
    override val itemHash = hashCode()

    val statusDrawable
        get() = when (status) {
            STATUS_SIGN -> R.drawable.quality_sign
            STATUS_VIOLATION -> R.drawable.with_violation
            else -> 0
        }

    val favoriteDrawable
        get() = when (isFavorite) {
            true -> R.drawable.ic_favorite
            false, null -> R.drawable.ic_favorite_border
        }

    val fullImageUrl
        get() = urlImage.toFullImageUrl()
}
