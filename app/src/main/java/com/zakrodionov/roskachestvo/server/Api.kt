package com.zakrodionov.roskachestvo.server

import com.zakrodionov.roskachestvo.model.ProductInfoResp
import com.zakrodionov.roskachestvo.model.ProductResp
import com.zakrodionov.roskachestvo.model.ProductsResp
import com.zakrodionov.roskachestvo.model.ResearchesResp
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("getproduct/{id}")
    fun getProductByBarcode(@Path("id") id: String): Single<ArrayList<ProductResp>>

    @GET("product/{id}")
    fun getProductInfo(@Path("id") id: String): Single<ArrayList<ProductInfoResp>>

    @GET("getproducts")
    fun getProducts(): Single<ArrayList<ProductsResp>>

    @GET("researches")
    fun getResearches(): Single<ArrayList<ResearchesResp>>
}