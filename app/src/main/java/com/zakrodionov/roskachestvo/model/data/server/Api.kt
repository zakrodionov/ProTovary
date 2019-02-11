package com.zakrodionov.roskachestvo.model.data.server

import com.zakrodionov.roskachestvo.entity.ProductInfo
import com.zakrodionov.roskachestvo.entity.Product
import com.zakrodionov.roskachestvo.entity.Products
import com.zakrodionov.roskachestvo.entity.Researches
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