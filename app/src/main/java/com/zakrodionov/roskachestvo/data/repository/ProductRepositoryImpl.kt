package com.zakrodionov.roskachestvo.data.repository

import com.zakrodionov.roskachestvo.app.platform.ErrorHandler
import com.zakrodionov.roskachestvo.data.db.ProductDao
import com.zakrodionov.roskachestvo.data.network.Api
import com.zakrodionov.roskachestvo.domain.entity.Product
import com.zakrodionov.roskachestvo.domain.entity.ProductInfo
import com.zakrodionov.roskachestvo.domain.entity.Products
import com.zakrodionov.roskachestvo.domain.repository.ProductRepository

class ProductRepositoryImpl(private val api: Api,
                            private val productDao: ProductDao,
                            private val errorHandler: ErrorHandler) : ProductRepository {

    override fun getProduct(id: String): ArrayList<Product> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProductInfo(id: String): ArrayList<ProductInfo> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getProducts(): ArrayList<Products> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}