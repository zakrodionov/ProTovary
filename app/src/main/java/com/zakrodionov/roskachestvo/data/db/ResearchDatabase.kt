package com.zakrodionov.roskachestvo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zakrodionov.roskachestvo.domain.entity.ResearchCompact
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory

@Database(entities = [ResearchesCategory::class, ResearchCompact::class], version = 1, exportSchema = false)
abstract class ResearchDatabase : RoomDatabase() {
    abstract val researchDao: ResearchDao
}