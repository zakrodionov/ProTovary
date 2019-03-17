package com.zakrodionov.protovary.domain.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Summary(
    @SerializedName("highquality")
    var highquality: String?,
    @SerializedName("quality")
    var quality: String?,
    @SerializedName("withsign")
    var withsign: String?,
    @SerializedName("withviolation")
    var withviolation: String?
) : Serializable