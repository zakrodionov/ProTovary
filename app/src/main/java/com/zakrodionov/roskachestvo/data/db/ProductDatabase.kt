package com.zakrodionov.roskachestvo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
}