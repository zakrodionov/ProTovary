package com.zakrodionov.roskachestvo.entity

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("url")
    val url: String? = ""
)