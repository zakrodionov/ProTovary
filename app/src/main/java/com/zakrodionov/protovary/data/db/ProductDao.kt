package com.zakrodionov.protovary.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct

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

    @Transaction
    suspend fun actionFavorite(favoriteProduct: FavoriteProduct) {
        val isFavorite = productIsFavorite(favoriteProduct.id) > 0

        if (isFavorite) {
            deleteFavoriteProduct(favoriteProduct.id)
        } else {
            addFavoriteProduct(favoriteProduct)
        }

    }
}
