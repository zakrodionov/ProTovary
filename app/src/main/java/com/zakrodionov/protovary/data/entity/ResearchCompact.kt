package com.zakrodionov.protovary.data.entity

import android.os.Parcelable
import com.zakrodionov.protovary.app.platform.DisplayableItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResearchCompact(
    val id: Long,
    val category: Int?,
    val image: Image?,
    val name: String?,
    val summary: Summary?,
    val url: String?,
    val utime: Long?
) : DisplayableItem, Parcelable
