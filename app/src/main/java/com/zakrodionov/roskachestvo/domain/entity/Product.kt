package com.zakrodionov.roskachestvo.domain.entity

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("+")
    val pros: List<Any?>?,
    @SerializedName("-")
    val cons: List<String?>?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("indicators")
    val indicators: List<Indicator?>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("points")
    val points: String?,
    @SerializedName("producer")
    val producer: String?,
    @SerializedName("properties")
    val properties: List<Property?>?,
    @SerializedName("research_results")
    val researchResults: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("trademark")
    val trademark: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("violations")
    val violations: String?
) {
    data class Indicator(
        @SerializedName("name")
        val name: String?,
        @SerializedName("value")
        val value: String?
    )
}