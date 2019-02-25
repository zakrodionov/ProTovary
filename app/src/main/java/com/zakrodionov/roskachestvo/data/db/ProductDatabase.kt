package com.zakrodionov.roskachestvo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zakrodionov.roskachestvo.domain.entity.Product

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
}