package com.zakrodionov.roskachestvo.domain.entity

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
    val productsInfo: List<ProductsInfo>?,
    @SerializedName("statistics")
    val statistics: Any?,
    @SerializedName("url")
    val url: String?
)