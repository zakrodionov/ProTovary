package com.zakrodionov.roskachestvo.data.network

import com.zakrodionov.roskachestvo.domain.entity.ProductInfo
import com.zakrodionov.roskachestvo.domain.entity.Product
import com.zakrodionov.roskachestvo.domain.entity.Products
import com.zakrodionov.roskachestvo.domain.entity.Researches
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("getproduct/{id}")
    fun getProductByBarcode(@Path("id") id: String): Single<ArrayList<Product>>

    @GET("product/{id}")
    fun getProductInfo(@Path("id") id: String): Single<ArrayList<ProductInfo>>

    @GET("getproducts")
    fun getProducts(): Single<ArrayList<Products>>

    @GET("researches")
    fun getResearches(): Single<ArrayList<Researches>>
}