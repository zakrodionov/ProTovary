package com.zakrodionov.protovary.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.domain.entity.ProductInfo

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

    @Query("SELECT * FROM productinfo ")
    fun getProducts(): LiveData<List<ProductInfo>>


    @Query("UPDATE productinfo SET isFavorite = (SELECT favoriteproduct.isFavorite FROM favoriteproduct WHERE productinfo.id = favoriteproduct.id)")
    fun updateProducts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProducts(list: List<ProductInfo>)

    @Query("DELETE FROM productinfo")
    fun deleteProducts()

    @Transaction
    fun refreshProducts(list: List<ProductInfo>){
        deleteProducts()
        insertProducts(list)
        updateProducts()
    }

    @Transaction
    fun actionFavorite(favoriteProduct: FavoriteProduct): Boolean {
        val isFavorite = productIsFavorite(favoriteProduct.id) > 0
        val flag: Boolean

        if (isFavorite) {
            deleteById(favoriteProduct.id)
            flag = false
        } else {
            insertFavoriteProduct(favoriteProduct)
            flag = true
        }

        updateProducts()

        return flag
    }
}