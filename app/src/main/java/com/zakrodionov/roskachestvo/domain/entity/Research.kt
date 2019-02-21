package com.zakrodionov.roskachestvo.domain.entity

import com.google.gson.annotations.SerializedName

data class Research(
    @SerializedName("category")
    val category: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("image")
    val image: Image?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("summary")
    val summary: Summary?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("utime")
    val utime: Int?
)