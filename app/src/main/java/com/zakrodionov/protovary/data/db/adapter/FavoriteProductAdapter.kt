package com.zakrodionov.protovary.data.db.adapter

import com.zakrodionov.protovary.data.db.entity.FavoriteProduct
import com.zakrodionov.protovary.domain.entity.Product
import com.zakrodionov.protovary.domain.entity.ProductInfo

object FavoriteProductAdapter {

    fun productToStore(product: Product, id: Long) =
        FavoriteProduct(
            id = id,
            name = product.name ?: "",
            urlImage = product.image?.src ?: "",
            trademark = product.trademark ?: "",
            status = product.status ?: "",
            points = product.points ?: 0.00
        )

    fun productToStore(product: ProductInfo) =
        FavoriteProduct(
            id = product.id,
            name = product.name ?: "",
            urlImage = product.image?.src ?: "",
            trademark = product.trademark ?: "",
            status = product.status ?: "",
            points = product.points ?: 0.00
        )
}