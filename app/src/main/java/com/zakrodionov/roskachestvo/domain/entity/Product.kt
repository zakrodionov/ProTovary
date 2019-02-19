package com.zakrodionov.roskachestvo.domain.entity

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("url")
    val url: String? = ""
)