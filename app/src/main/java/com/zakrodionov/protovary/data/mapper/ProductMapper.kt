package com.zakrodionov.protovary.data.mapper

import android.content.Context
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
            isFavorite = null
        )

    fun productInfoToProduct(product: ProductInfo) =
        Product(
            id = product.id,
            name = product.name ?: "",
            urlImage = product.image?.src ?: "",
            trademark = product.trademark ?: "",
            status = product.status ?: "",
            points = product.points ?: 0.00,
            isFavorite = product.isFavorite
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
            isFavorite = product.isFavorite
        )
}
