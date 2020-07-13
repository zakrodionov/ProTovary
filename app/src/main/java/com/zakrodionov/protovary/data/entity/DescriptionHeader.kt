package com.zakrodionov.protovary.data.entity

import java.io.Serializable

data class DescriptionHeader(
    val date: Long?,
    val description: String?
) : Serializable
