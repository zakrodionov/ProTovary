package com.zakrodionov.protovary.domain.model

import androidx.annotation.DrawableRes
import java.io.Serializable

data class Product(
    val id: Long,
    val urlImage: String,
    val name: String,
    val points: Double,
    val status: String,
    val trademark: String,
    val isFavorite: Boolean?,

    var fullImageUrl: String,
    @DrawableRes
    var favoriteDrawable: Int,
    @DrawableRes
    var statusDrawable: Int
) : Serializable
