package com.zakrodionov.protovary.domain.entity

import com.google.gson.annotations.SerializedName

data class Indicator(
    @SerializedName("group")
    val group: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("value")
    val value: String?
)