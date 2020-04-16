package com.zakrodionov.protovary.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Image(
    @SerializedName("height")
    var height: Int?,
    @SerializedName("size")
    var size: Int?,
    @SerializedName("src")
    var src: String?,
    @SerializedName("width")
    var width: Int?
) : Serializable
