package com.zakrodionov.protovary.data.entity

import com.google.gson.annotations.SerializedName

data class ProductCompact(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)