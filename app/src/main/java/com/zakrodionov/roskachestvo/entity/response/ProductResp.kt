package com.zakrodionov.roskachestvo.entity.response

import com.google.gson.annotations.SerializedName

data class ProductResp(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("url")
    val url: String? = ""
)