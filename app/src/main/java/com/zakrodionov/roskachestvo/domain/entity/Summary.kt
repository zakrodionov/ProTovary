package com.zakrodionov.roskachestvo.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Summary(
    @SerializedName("highquality")
    val highquality: String?,
    @SerializedName("quality")
    val quality: String?,
    @SerializedName("withsign")
    val withsign: String?,
    @SerializedName("withviolation")
    val withviolation: String?
) : Serializable