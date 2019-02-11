package com.zakrodionov.roskachestvo.entity

import com.google.gson.annotations.SerializedName

data class Researches(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("image")
    val image: Image? = Image(),
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("researches")
    val researches: List<Research?>? = listOf(),
    @SerializedName("utime")
    val utime: Int? = 0
) {
    data class Research(
        @SerializedName("category")
        val category: String? = "",
        @SerializedName("id")
        val id: String? = "",
        @SerializedName("image")
        val image: Image? = Image(),
        @SerializedName("name")
        val name: String? = "",
        @SerializedName("summary")
        val summary: Summary? = Summary(),
        @SerializedName("url")
        val url: String? = "",
        @SerializedName("utime")
        val utime: Int? = 0
    ) {
        data class Summary(
            @SerializedName("highquality")
            val highquality: String? = "",
            @SerializedName("quality")
            val quality: String? = "",
            @SerializedName("withsign")
            val withsign: String? = "",
            @SerializedName("withviolation")
            val withviolation: String? = ""
        )
    }
}