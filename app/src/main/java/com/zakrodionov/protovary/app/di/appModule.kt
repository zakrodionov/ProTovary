package com.zakrodionov.protovary.app.di

import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.zakrodionov.protovary.BuildConfig
import com.zakrodionov.protovary.R
import com.zakrodionov.protovary.app.platform.ErrorHandler
import com.zakrodionov.protovary.app.platform.NetworkHandler
import com.zakrodionov.protovary.app.platform.ResourceManager
import com.zakrodionov.protovary.data.db.AppDatabase
import com.zakrodionov.protovary.data.mapper.ProductMapper
import com.zakrodionov.protovary.data.network.Api
import com.zakrodionov.protovary.data.repository.ProductRepository
import com.zakrodionov.protovary.data.repository.ResearchRepository
import com.zakrodionov.protovary.domain.interactor.product.ProductInteractor
import com.zakrodionov.protovary.domain.interactor.research.ResearchInteractor
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { buildRetrofit() }

    single { buildOkHttp() }

    single { buildApi() }

    single { buildDataBase(get()) }

    single {
        get<AppDatabase>().researchDao
    }

    single {
        get<AppDatabase>().productDao
    }

    // Data&Domain layers
    single { NetworkHandler(get()) }
    single { ErrorHandler(get()) }

    single { ProductRepository(get(), get()) }
    single { ResearchRepository(get(), get()) }

    single { ProductInteractor(get(), get(), get(), get()) }
    single { ResearchInteractor(get(), get(), get()) }

    single { ProductMapper(get()) }
    single { ResourceManager(get()) }
}

private fun Scope.buildDataBase(resourceManager: ResourceManager) =
    Room.databaseBuilder(get(), AppDatabase::class.java, resourceManager.getString(R.string.db_name)).build()

private fun Scope.buildApi(): Api = get<Retrofit>().create(Api::class.java)

private fun buildOkHttp(): OkHttpClient {
    val okHttpClientBuilder = OkHttpClient.Builder()
    okHttpClientBuilder.connectTimeout(50, TimeUnit.SECONDS)
    okHttpClientBuilder.readTimeout(50, TimeUnit.SECONDS)

    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpClientBuilder.addInterceptor(loggingInterceptor)
        okHttpClientBuilder.addNetworkInterceptor(StethoInterceptor())
    }

    return okHttpClientBuilder.build()
}

private fun Scope.buildRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_ENDPOINT)
        .client(get())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
