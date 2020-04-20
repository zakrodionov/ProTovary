package com.zakrodionov.protovary.app.util.enums

enum class ResearchSortType(val value: String, val direction: String) {
    BY_RATING_DECREASE("points", "DESC"),
    BY_RATING_INCREASE("points", "ASC"),
    BY_TRADEMARK("trademark", "ASC")
}
