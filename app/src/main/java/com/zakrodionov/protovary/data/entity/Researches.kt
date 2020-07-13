package com.zakrodionov.protovary.data.entity

import android.os.Parcelable
import com.zakrodionov.protovary.app.platform.DisplayableItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Researches(
    val id: Long,
    val image: Image?,
    val name: String?,
    val researches: List<ResearchCompact>?,
    val utime: Int?
) : DisplayableItem, Parcelable
