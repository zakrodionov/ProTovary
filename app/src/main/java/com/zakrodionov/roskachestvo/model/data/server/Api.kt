package com.zakrodionov.roskachestvo.model.data.server

import com.zakrodionov.roskachestvo.entity.response.ProductInfoResp
import com.zakrodionov.roskachestvo.entity.response.ProductResp
import com.zakrodionov.roskachestvo.entity.response.ProductsResp
import com.zakrodionov.roskachestvo.entity.response.ResearchesResp
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