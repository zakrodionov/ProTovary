package com.zakrodionov.roskachestvo.domain.entity

import com.google.gson.annotations.SerializedName

data class ProductsInfo(
    @SerializedName("barcode")
    val barcode: Any?,
    @SerializedName("category")
    val category: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("points")
    val points: String?,
    @SerializedName("producer")
    val producer: Boolean?,
    @SerializedName("status")
    val status: Any?,
    @SerializedName("trademark")
    val trademark: String?,
    @SerializedName("url")
    val url: String?
)