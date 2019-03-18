package com.zakrodionov.protovary.domain.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ProductInfo(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,

    @SerializedName("barcode")
    val barcode: String?,

    @SerializedName("category")
    val category: Int?,

    @Embedded
    @SerializedName("image")
    val image: Image?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("points")
    val points: Double?,

    @SerializedName("status")
    val status: String?,

    @SerializedName("producer")
    val producer: Boolean?,

    @SerializedName("trademark")
    val trademark: String?,

    @SerializedName("url")
    val url: String?,

    val isFavorite: Boolean?
)