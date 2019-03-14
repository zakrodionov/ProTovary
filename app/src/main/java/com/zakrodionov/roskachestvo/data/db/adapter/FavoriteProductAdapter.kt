package com.zakrodionov.roskachestvo.data.db.adapter

import com.zakrodionov.roskachestvo.data.db.entity.FavoriteProduct
import com.zakrodionov.roskachestvo.domain.entity.Product

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
}