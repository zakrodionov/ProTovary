package com.zakrodionov.roskachestvo.app.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.zakrodionov.roskachestvo.BuildConfig
import com.zakrodionov.roskachestvo.app.AndroidApplication
import com.zakrodionov.roskachestvo.app.platform.ErrorHandler
import com.zakrodionov.roskachestvo.app.platform.NetworkHandler
import com.zakrodionov.roskachestvo.data.db.ProductDao
import com.zakrodionov.roskachestvo.data.db.ProductDatabase
import com.zakrodionov.roskachestvo.data.network.Api
import com.zakrodionov.roskachestvo.data.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context = application

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://roskachestvo.gov.ru/api/")
            .client(createClient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)


    @Provides
    @Singleton
    fun provideProductRepository(api: Api, productDao: ProductDao, errorHandler: ErrorHandler): ProductRepositoryImpl =
        ProductRepositoryImpl(api, productDao, errorHandler)

    @Provides
    @Singleton
    fun provideDb(app: Context): ProductDatabase =
        Room.databaseBuilder(app, ProductDatabase::class.java, "main.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideMovieDao(db: ProductDatabase): ProductDao = db.productDao

    @Provides
    @Singleton
    fun provideErrorHandler(networkHandler: NetworkHandler): ErrorHandler = ErrorHandler(networkHandler)

}
