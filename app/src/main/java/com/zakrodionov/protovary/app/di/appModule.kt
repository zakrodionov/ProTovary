package com.zakrodionov.protovary.app.di

import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.app.platform.ErrorHandler
import com.zakrodionov.protovary.app.platform.NetworkHandler
import com.zakrodionov.protovary.app.ui.favorites.ProductsFavoriteAdapter
import com.zakrodionov.protovary.app.ui.research.adapter.ProductsAdapter
import com.zakrodionov.protovary.app.ui.researches.ResearchesAdapter
import com.zakrodionov.protovary.app.ui.researchescategory.ResearchesCategoryAdapter
import com.zakrodionov.protovary.data.db.AppDatabase
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.data.network.Api
import com.zakrodionov.protovary.data.repository.ProductRepository
import com.zakrodionov.protovary.data.repository.ResearchRepository
import com.zakrodionov.protovary.domain.interactor.product.ProductInteractor
import com.zakrodionov.protovary.domain.interactor.research.ResearchInteractor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {

    //Retrofit
    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.API_ENDPOINT)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //OkHttpClient
    single {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(50, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(50, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
            okHttpClientBuilder.addNetworkInterceptor(StethoInterceptor())
        }

        okHttpClientBuilder.build()
    }

    //Api
    single {
        val retrofit: Retrofit by inject()
        retrofit.create(Api::class.java)
    }

    //Database
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "roskachestvo.db").build() }

    single {
        val db: AppDatabase by inject()
        db.researchDao
    }

    single {
        val db: AppDatabase by inject()
        db.productDao
    }

    //Data/Domain layers
    single { NetworkHandler(get()) }
    single { ErrorHandler(get()) }

    single { ProductRepository(get(), get()) }
    single { ResearchRepository(get(), get()) }

    single { ProductInteractor(get(), get(), get(), get()) }
    single { ResearchInteractor(get(), get(), get()) }

    single { ProductMapper(get()) }

}