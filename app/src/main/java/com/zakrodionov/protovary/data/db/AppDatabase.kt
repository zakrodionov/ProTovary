package com.zakrodionov.protovary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.data.entity.ProductInfo
import com.zakrodionov.protovary.data.entity.ResearchCompact
import com.zakrodionov.protovary.data.entity.Researches

@Database(
    entities = [Researches::class, ResearchCompact::class, FavoriteProduct::class, ProductInfo::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val researchDao: ResearchDao
    abstract val productDao: ProductDao

}