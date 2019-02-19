package com.zakrodionov.roskachestvo.domain.entity

import com.google.gson.annotations.SerializedName

data class Products(
    @SerializedName("barcode")
    val barcode: Long? = 0,
    @SerializedName("category")
    val category: String? = "",
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("image")
    val image: Image? = Image(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("producer")
    val producer: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("trademark")
    val trademark: String? = "",
    @SerializedName("url")
    val url: String? = ""
)