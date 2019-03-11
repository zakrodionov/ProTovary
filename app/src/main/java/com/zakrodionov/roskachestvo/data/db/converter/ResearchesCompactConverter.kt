package com.zakrodionov.roskachestvo.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.zakrodionov.roskachestvo.domain.entity.ResearchCompact

class ResearchesCompactConverter {

    @TypeConverter
    fun fromResearchesList(researches: List<ResearchCompact>?): String? {
        if (researches == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<ResearchCompact>>() {}.type
        return gson.toJson(researches, type)
    }

    @TypeConverter
    fun toResearchesList(researches: String?): List<ResearchCompact>? {
        if (researches == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<ResearchCompact>>() {}.type
        return gson.fromJson<List<ResearchCompact>>(researches, type)
    }
}
