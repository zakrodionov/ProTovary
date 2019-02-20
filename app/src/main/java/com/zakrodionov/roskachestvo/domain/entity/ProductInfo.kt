package com.zakrodionov.roskachestvo.domain.entity

import com.google.gson.annotations.SerializedName

data class ProductInfo(
    @SerializedName("+")
    val pluses: List<String>?,
    @SerializedName("-")
    val minuses: List<String>?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("indicators")
    val indicators: List<Indicator>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("producer")
    val producer: String?,
    @SerializedName("properties")
    val properties: List<Property>?,
    @SerializedName("research_results")
    val researchResults: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("trademark")
    val trademark: String? ,
    @SerializedName("url")
    val url: String? ,
    @SerializedName("violations")
    val violations: Any?
)