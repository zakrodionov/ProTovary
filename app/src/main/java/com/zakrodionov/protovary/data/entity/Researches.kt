package com.zakrodionov.protovary.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.zakrodionov.protovary.data.db.converter.ResearchesCompactConverter

@Entity
@TypeConverters(ResearchesCompactConverter::class)
data class Researches(
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