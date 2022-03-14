package com.example.weatherapp.di

import android.app.Application
import android.content.Context
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.utils.ConnectionLiveData
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .client(OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(HttpLoggingInterceptor().also { httpLoggingInterceptor ->
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                })
            }
        }.build())
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideConnectionLiveData(@ApplicationContext context: Context) =
        ConnectionLiveData(context as Application)
}