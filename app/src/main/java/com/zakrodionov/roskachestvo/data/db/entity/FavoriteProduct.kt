package com.zakrodionov.roskachestvo.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteProduct (
    @PrimaryKey
    val id: Long,
    val name: String,
    val urlImage: String,
    val trademark: String,
    val points: Double
)