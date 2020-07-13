package com.zakrodionov.protovary.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.data.entity.ProductInfo

@Database(
    entities = [FavoriteProduct::class, ProductInfo::class],
    version = 2, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract val productDao: ProductDao
}
