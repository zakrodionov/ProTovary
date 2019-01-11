package com.zakrodionov.roskachestvo.model

import com.google.gson.annotations.SerializedName

data class ProductInfoResp(
    @SerializedName("+")
    val pluses: List<String?>? = listOf(),
    @SerializedName("-")
    val minuses: List<String?>? = listOf(),
    @SerializedName("image")
    val image: Image? = Image(),
    @SerializedName("indicators")
    val indicators: List<Indicator?>? = listOf(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("producer")
    val producer: String? = "",
    @SerializedName("properties")
    val properties: List<Property?>? = listOf(),
    @SerializedName("research_results")
    val researchResults: String? = "",
    @SerializedName("status")
    val status: String? = "",
    @SerializedName("trademark")
    val trademark: String? = "",
    @SerializedName("url")
    val url: String? = "",
    @SerializedName("violations")
    val violations: Any? = Any()
) {
    data class Property(
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("value")
        val value: String? = ""
    )

    data class Indicator(
        @SerializedName("group")
        val group: String? = "",
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("value")
        val value: String? = ""
    )

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