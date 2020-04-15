package com.zakrodionov.protovary.data.entity

import com.google.gson.annotations.SerializedName

data class ProductDto(
    @SerializedName("barcode")
    val barcode: Long?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("producer")
    val producer: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("trademark")
    val trademark: String?,
    @SerializedName("url")
    val url: String?
)
