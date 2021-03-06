package com.zakrodionov.protovary.data.entity

import com.google.gson.annotations.SerializedName

data class Research(
    @SerializedName("anons")
    val anons: String?,
    @SerializedName("category")
    val category: Int?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("products")
    val products: List<String>?,
    @SerializedName("products_info")
    val productInfo: List<ProductInfo>?,
    @SerializedName("statistics")
    val statistics: Any?,
    @SerializedName("url")
    val url: String?
)
