package com.zakrodionov.roskachestvo.domain.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName
import com.zakrodionov.roskachestvo.data.db.converter.ResearchesCompactConverter

@Entity
@TypeConverters(ResearchesCompactConverter::class)
data class ResearchesCategory(
    @PrimaryKey
    @SerializedName("id")
    var id: Long,

    @Embedded
    @SerializedName("image")
    var image: Image?,

    @SerializedName("name")
    var name: String?,

    @TypeConverters(ResearchesCompactConverter::class)
    @SerializedName("researches")
    var researches: List<ResearchCompact>?,

    @SerializedName("utime")
    var utime: Int?
)