package com.zakrodionov.protovary.domain.model

import android.content.Context
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.R
import java.io.Serializable

data class Product(
    val id: Long,
    val urlImage: String,
    val name: String,
    val points: Double,
    val status: String,
    val trademark: String,
    val isFavorite: Boolean?
) : Serializable {

    fun getStatusDrawable(context: Context): Int {
        when (status) {
            context.getString(R.string.status_sign) -> return R.drawable.quality_sign
            context.getString(R.string.status_violation) -> return R.drawable.with_violation
            else -> return 0
        }
    }

    fun getFavoriteDrawable(context: Context): Int {
        when (isFavorite) {
            true -> return R.drawable.ic_favorite
            false, null -> return R.drawable.ic_favorite_border
        }
    }

    fun fullImageUrl() = "${BuildConfig.API_ENDPOINT.substringBeforeLast("api/")}$urlImage"

    fun trademarkAndNameIsSame() = trademark == name
}