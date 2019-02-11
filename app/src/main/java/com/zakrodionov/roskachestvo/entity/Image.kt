package com.zakrodionov.roskachestvo.entity

import com.google.gson.annotations.SerializedName

data class Image(
            @SerializedName("height")
            val height: Int? = 0,
            @SerializedName("size")
            val size: Int? = 0,
            @SerializedName("src")
            val src: String? = "",
            @SerializedName("width")
            val width: Int? = 0
)