package com.zakrodionov.protovary.data.network

import com.zakrodionov.protovary.domain.entity.*
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("getproduct/{id}")
    fun getProductByBarcode(@Path("id") id: String): Deferred<ProductCompact>

    @GET("product/{id}")
    fun getProduct(@Path("id") id: Long): Deferred<Product>

    @GET("getproducts")
    fun getProducts(): Deferred<List<Products>>

    @GET("researches")
    fun getResearches(): Deferred<List<Researches>>

    @GET("research/{id}")
    fun getResearch(@Path("id") id: Long): Deferred<Research>
}