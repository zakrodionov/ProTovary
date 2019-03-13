package com.zakrodionov.roskachestvo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zakrodionov.roskachestvo.data.db.entity.FavoriteProduct
import com.zakrodionov.roskachestvo.domain.entity.ResearchCompact
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory

@Database(
    entities = [ResearchesCategory::class, ResearchCompact::class, FavoriteProduct::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val researchDao: ResearchDao
    abstract val productDao: ProductDao

}