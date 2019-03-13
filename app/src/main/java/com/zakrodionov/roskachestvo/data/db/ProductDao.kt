package com.zakrodionov.roskachestvo.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zakrodionov.roskachestvo.data.db.entity.FavoriteProduct
import com.zakrodionov.roskachestvo.domain.entity.ResearchCompact
import com.zakrodionov.roskachestvo.domain.entity.ResearchesCategory

@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteProduct(favoriteProduct: FavoriteProduct)

    @Query("SELECT COUNT(*) FROM favoriteproduct WHERE id = :id ")
    fun productIsFavoriteLive(id: Long): LiveData<Int>

    @Query("SELECT COUNT(*) FROM favoriteproduct WHERE id = :id ")
    fun productIsFavorite(id: Long): Int

    @Query("DELETE FROM favoriteproduct WHERE id = :id")
    fun deleteById(id: Long)

    @Query("SELECT * FROM favoriteproduct ")
    fun getFavoriteProducts(): LiveData<List<FavoriteProduct>>

    @Transaction
    fun actionFavorite(favoriteProduct: FavoriteProduct): Boolean {
        val isFavorite = productIsFavorite(favoriteProduct.id) > 0

        if (isFavorite){
            deleteById(favoriteProduct.id)
            return false
        } else {
            insertFavoriteProduct(favoriteProduct)
            return true
        }
    }
}