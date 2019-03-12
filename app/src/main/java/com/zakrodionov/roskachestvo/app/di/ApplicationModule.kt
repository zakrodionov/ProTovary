package com.zakrodionov.roskachestvo.app.di

import android.content.Context
import androidx.room.Room
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.zakrodionov.roskachestvo.BuildConfig
import com.zakrodionov.roskachestvo.app.AndroidApplication
import com.zakrodionov.roskachestvo.app.platform.ErrorHandler
import com.zakrodionov.roskachestvo.app.platform.NetworkHandler
import com.zakrodionov.roskachestvo.data.db.ResearchDao
import com.zakrodionov.roskachestvo.data.db.AppDatabase
import com.zakrodionov.roskachestvo.data.db.ProductDao
import com.zakrodionov.roskachestvo.data.network.Api
import com.zakrodionov.roskachestvo.data.repository.ProductRepositoryImpl
import com.zakrodionov.roskachestvo.data.repository.ResearchesRepositoryImpl
import com.zakrodionov.roskachestvo.domain.repository.ProductRepository
import com.zakrodionov.roskachestvo.domain.repository.ResearchesRepository
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
            .baseUrl(BuildConfig.API_ENDPOINT)
            .client(createClient())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun createClient(): OkHttpClient {
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(loggingInterceptor)
            okHttpClientBuilder.addNetworkInterceptor(StethoInterceptor())
        }

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)


    @Provides
    @Singleton
    fun provideProductRepository(api: Api, productDao: ProductDao, errorHandler: ErrorHandler): ProductRepository =
        ProductRepositoryImpl(api, productDao, errorHandler)

    @Provides
    @Singleton
    fun provideResearchesRepository(
        api: Api,
        researchDao: ResearchDao,
        errorHandler: ErrorHandler
    ): ResearchesRepository =
        ResearchesRepositoryImpl(api, researchDao, errorHandler)

    @Provides
    @Singleton
    fun provideDb(app: Context): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "roskachestvo.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideResearchDao(db: AppDatabase): ResearchDao = db.researchDao

    @Provides
    @Singleton
    fun provideProductDao(db: AppDatabase): ProductDao = db.productDao

    @Provides
    @Singleton
    fun provideErrorHandler(networkHandler: NetworkHandler): ErrorHandler = ErrorHandler(networkHandler)

}
