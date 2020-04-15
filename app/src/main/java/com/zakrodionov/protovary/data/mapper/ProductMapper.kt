package com.zakrodionov.protovary.data.mapper

import android.content.Context
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.util.Utils
import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.data.entity.ProductDetail
import com.zakrodionov.protovary.data.entity.ProductInfo
import com.zakrodionov.protovary.domain.model.Product

class ProductMapper(val context: Context) {

    fun productDetailToProduct(product: ProductDetail, id: Long) =
        Product(
            id = id,
            name = product.name ?: "",
            urlImage = product.image?.src ?: "",
            trademark = product.trademark ?: "",
            status = product.status ?: "",
            points = product.points ?: 0.00,
            isFavorite = null,
            fullImageUrl = fullImageUrl(product.image?.src ?: ""),
            favoriteDrawable = getFavoriteDrawable(null),
            statusDrawable = getStatusDrawable(product.status ?: "", context)
        )

    fun productInfoToProduct(product: ProductInfo) =
        Product(
            id = product.id,
            name = product.name ?: "",
            urlImage = product.image?.src ?: "",
            trademark = product.trademark ?: "",
            status = product.status ?: "",
            points = product.points ?: 0.00,
            isFavorite = product.isFavorite,
            fullImageUrl = fullImageUrl(product.image?.src ?: ""),
            favoriteDrawable = getFavoriteDrawable(product.isFavorite),
            statusDrawable = getStatusDrawable(product.status ?: "", context)
        )

    fun productToStore(product: Product) =
        FavoriteProduct(
            id = product.id,
            name = product.name,
            urlImage = product.urlImage,
            trademark = product.trademark,
            status = product.status,
            points = product.points,
            isFavorite = true
        )

    fun productFromStore(product: FavoriteProduct) =
        Product(
            id = product.id,
            name = product.name,
            urlImage = product.urlImage,
            trademark = product.trademark,
            status = product.status,
            points = product.points,
            isFavorite = product.isFavorite,
            fullImageUrl = fullImageUrl(product.urlImage),
            favoriteDrawable = getFavoriteDrawable(product.isFavorite),
            statusDrawable = getStatusDrawable(product.status, context)
        )

    // helper methods
    fun getStatusDrawable(status: String, context: Context): Int {
        when (status) {
            context.getString(R.string.status_sign) -> return R.drawable.quality_sign
            context.getString(R.string.status_violation) -> return R.drawable.with_violation
            else -> return 0
        }
    }

    fun getFavoriteDrawable(isFavorite: Boolean?): Int {
        when (isFavorite) {
            true -> return R.drawable.ic_favorite
            false, null -> return R.drawable.ic_favorite_border
        }
    }

    fun fullImageUrl(urlImage: String) = "${Utils.baseImageUrl()}$urlImage"
}
