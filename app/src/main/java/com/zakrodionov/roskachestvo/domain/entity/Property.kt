package com.zakrodionov.roskachestvo.domain.entity

import com.google.gson.annotations.SerializedName

data class Property(
        @SerializedName("name")
        val name: String?,
        @SerializedName("value")
        val value: String?
    )