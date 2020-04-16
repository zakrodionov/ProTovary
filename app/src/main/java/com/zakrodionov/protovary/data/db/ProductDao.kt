package com.zakrodionov.protovary.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.data.entity.ProductInfo
import kotlinx.coroutines.flow.Flow

/*suspend для LiveData не требуется*/
@Dao
interface ProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteProduct(favoriteProduct: FavoriteProduct)

    @Query("SELECT COUNT(*) FROM favoriteproduct WHERE id = :id ")
    suspend fun productIsFavorite(id: Long): Int

    @Query("DELETE FROM favoriteproduct WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT COUNT(*) FROM favoriteproduct WHERE id = :id ")
    fun productIsFavoriteLive(id: Long): LiveData<Int>

    @Query("SELECT * FROM favoriteproduct ")
    fun getFavoriteProducts(): LiveData<List<FavoriteProduct>>

    @Query("SELECT * FROM productinfo ")
    fun getProducts(): Flow<List<ProductInfo>>

    @Query("UPDATE productinfo SET isFavorite = (SELECT favoriteproduct.isFavorite FROM favoriteproduct WHERE productinfo.id = favoriteproduct.id)")
    suspend fun updateProducts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(list: List<ProductInfo>)

    @Query("DELETE FROM productinfo")
    suspend fun deleteProducts()

    @Transaction
    suspend fun refreshProducts(list: List<ProductInfo>) {
        deleteProducts()
        insertProducts(list)
        updateProducts()
    }

    @Transaction
    suspend fun actionFavorite(favoriteProduct: FavoriteProduct) {
        val isFavorite = productIsFavorite(favoriteProduct.id) > 0

        if (isFavorite) {
            deleteById(favoriteProduct.id)
        } else {
            insertFavoriteProduct(favoriteProduct)
        }

        updateProducts()
    }
}
