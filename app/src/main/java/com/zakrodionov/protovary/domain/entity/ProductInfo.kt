package com.zakrodionov.protovary.domain.entity

import com.google.gson.annotations.SerializedName

data class ProductInfo(
    @SerializedName("barcode")
    val barcode: Any?,
    @SerializedName("category")
    val category: Int?,
    @SerializedName("id")
    val id: Long,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("points")
    val points: Double?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("producer")
    val producer: Boolean?,
    @SerializedName("trademark")
    val trademark: String?,
    @SerializedName("url")
    val url: String?
)