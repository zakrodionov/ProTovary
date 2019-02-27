package com.zakrodionov.roskachestvo.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Image(
    @SerializedName("height")
    val height: Int?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("src")
    val src: String?,
    @SerializedName("width")
    val width: Int?
) : Serializable