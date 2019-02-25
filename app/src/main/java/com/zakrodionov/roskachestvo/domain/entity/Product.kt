package com.zakrodionov.roskachestvo.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Product(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id: Long?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)