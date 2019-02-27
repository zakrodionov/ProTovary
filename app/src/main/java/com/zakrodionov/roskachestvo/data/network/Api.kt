package com.zakrodionov.roskachestvo.data.network

import com.zakrodionov.roskachestvo.domain.entity.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("getproduct/{id}")
    fun getProductByBarcode(@Path("id") id: String): Deferred<List<Product>>

    @GET("product/{id}")
    fun getProductInfo(@Path("id") id: String): Deferred<ProductInfo>

    @GET("getproducts")
    fun getProducts(): Deferred<List<Products>>

    @GET("researches")
    fun getResearches(): Deferred<List<ResearchesCategory>>

    @GET("research/{id}")
    fun getResearch(@Path("id") id: Long): Deferred<Research>
}