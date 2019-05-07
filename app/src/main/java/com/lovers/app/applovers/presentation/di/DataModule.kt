package com.lovers.app.applovers.presentation.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.lovers.app.applovers.App
import com.lovers.app.applovers.BuildConfig
import com.lovers.app.applovers.data.Api
import com.lovers.app.applovers.data.LocalRepository
import com.lovers.app.applovers.data.RemoteRepository
import com.lovers.app.applovers.domain.interfaces.ILocalRepository
import com.lovers.app.applovers.domain.interfaces.IRemoteRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class DataModule {

    companion object {
        private const val PREFS_NAME = "prefs_app_lovers"
    }

    @Provides
    @Singleton
    fun provideHttpBuilder(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(180, TimeUnit.SECONDS)
            .writeTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .baseUrl(BuildConfig.SERVER)
            .build()
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) = retrofit.create<Api>(Api::class.java)

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences = app.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideRemoteRepository(api: Api): IRemoteRepository = RemoteRepository(api)

    @Provides
    @Singleton
    fun provideLocalRepository(prefs: SharedPreferences): ILocalRepository = LocalRepository(prefs)

}