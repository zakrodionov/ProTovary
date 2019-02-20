package com.zakrodionov.roskachestvo.domain.repository

import com.zakrodionov.roskachestvo.domain.entity.Product
import com.zakrodionov.roskachestvo.domain.entity.ProductInfo
import com.zakrodionov.roskachestvo.domain.entity.Products
import com.zakrodionov.roskachestvo.domain.entity.Researches
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductRepository {

    fun getProduct(id: String): ArrayList<Product>

    fun getProductInfo(id: String): ArrayList<ProductInfo>

    fun getProducts(): ArrayList<Products>
}