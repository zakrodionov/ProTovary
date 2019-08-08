package com.zakrodionov.protovary.data.network

import com.zakrodionov.protovary.data.entity.*
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("getproduct/{id}")
    suspend fun getProductByBarcode(@Path("id") id: String): List<ProductCompact>

    @GET("product/{id}")
    suspend fun getProduct(@Path("id") id: Long): ProductDetail

    @GET("getproducts")
    suspend fun getProducts(): List<ProductDto>

    @GET("researches")
    suspend fun getResearches(): List<Researches>

    @GET("research/{id}")
    suspend fun getResearch(@Path("id") id: Long): Research
}