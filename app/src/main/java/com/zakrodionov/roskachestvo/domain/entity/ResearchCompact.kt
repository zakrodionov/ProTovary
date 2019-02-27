package com.zakrodionov.roskachestvo.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ResearchCompact(
    @SerializedName("category")
    val category: Int?,
    @SerializedName("id")
    val id: Long,
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
) : Serializable