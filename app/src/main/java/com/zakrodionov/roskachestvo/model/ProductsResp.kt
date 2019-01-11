package com.zakrodionov.roskachestvo.model

import com.google.gson.annotations.SerializedName

data class ProductsResp(
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
) {
    data class Image(
        @SerializedName("height")
        val height: Int? = 0,
        @SerializedName("size")
        val size: Int? = 0,
        @SerializedName("src")
        val src: String? = "",
        @SerializedName("width")
        val width: Int? = 0
    )
}