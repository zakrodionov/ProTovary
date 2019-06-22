package com.zakrodionov.protovary.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Indicator(
    @SerializedName("name")
    val name: String?,
    @SerializedName("value")
    val value: String?
) : Serializable