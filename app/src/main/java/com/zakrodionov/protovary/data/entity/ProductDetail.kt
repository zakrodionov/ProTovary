package com.zakrodionov.protovary.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductDetail(
    @SerializedName("+")
    val pros: List<String>?,
    @SerializedName("-")
    val cons: List<String>?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("indicators")
    val indicators: List<CommonNameValueData>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("points")
    val points: Double?,
    @SerializedName("producer")
    val producer: String?,
    @SerializedName("properties")
    val properties: List<CommonNameValueData>?,
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
) : Serializable