package com.zakrodionov.protovary.data.entity

import com.google.gson.annotations.SerializedName

data class Indicator(
    @SerializedName("name")
    val name: String?,
    @SerializedName("value")
    val value: String?
)