package com.zakrodionov.protovary.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SimpleSQLiteQuery
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.data.entity.ProductInfo
import kotlinx.coroutines.flow.Flow

/*suspend для LiveData не требуется*/
@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavoriteProduct(favoriteProduct: FavoriteProduct)

    @Query("DELETE FROM favoriteproduct WHERE id = :id")
    suspend fun deleteFavoriteProduct(id: Long)

    @Query("SELECT COUNT(*) FROM favoriteproduct WHERE id = :id ")
    suspend fun productIsFavorite(id: Long): Int

    @Query("SELECT COUNT(*) FROM favoriteproduct WHERE id = :id ")
    fun productIsFavoriteLive(id: Long): LiveData<Int>

    @Query("SELECT * FROM favoriteproduct ")
    fun getFavoriteProducts(): LiveData<List<FavoriteProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(list: List<ProductInfo>)

    @Query("DELETE FROM productinfo")
    suspend fun deleteProducts()

    @RawQuery(observedEntities = [ProductInfo::class])
    fun observeProducts(rawQuery: SimpleSQLiteQuery): Flow<List<ProductInfo>>

    @Transaction
    suspend fun refreshProducts(list: List<ProductInfo>) {
        deleteProducts()
        insertProducts(list)
        updateProducts()
    }

    @Query("UPDATE productinfo SET isFavorite = (SELECT favoriteproduct.isFavorite FROM favoriteproduct WHERE productinfo.id = favoriteproduct.id)")
    suspend fun updateProducts()

    @Transaction
    suspend fun actionFavorite(favoriteProduct: FavoriteProduct) {
        val isFavorite = productIsFavorite(favoriteProduct.id) > 0

        if (isFavorite) {
            deleteFavoriteProduct(favoriteProduct.id)
        } else {
            addFavoriteProduct(favoriteProduct)
        }

        updateProducts()
    }
}
