package com.zakrodionov.protovary.data.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class ResearchCompact(
    @PrimaryKey
    @SerializedName("id")
    var id: Long,

    @SerializedName("category")
    var category: Int?,

    @Embedded
    @SerializedName("image")
    var image: Image?,

    @SerializedName("name")
    var name: String?,

    @Embedded
    @SerializedName("summary")
    var summary: Summary?,

    @SerializedName("url")
    var url: String?,

    @SerializedName("utime")
    var utime: Int?
) : Serializable
