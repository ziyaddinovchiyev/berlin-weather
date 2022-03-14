package com.example.weatherapp.di

import com.example.weatherapp.data.remote.WeatherRemoteDataSource
import com.example.weatherapp.data.remote.WeatherService
import com.example.weatherapp.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object WeatherModule {

    @Provides
    @ViewModelScoped
    fun provideWeatherRepository(
        remoteDataSource: WeatherRemoteDataSource
    ): WeatherRepository = WeatherRepository(
        remoteDataSource = remoteDataSource
    )

    @Provides
    @ViewModelScoped
    fun provideWeatherRemoteDataSource(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        weatherService: WeatherService
    ): WeatherRemoteDataSource = WeatherRemoteDataSource(
        ioDispatcher = ioDispatcher,
        weatherService = weatherService
    )

    @Provides
    @ViewModelScoped
    fun provideWeatherService(retrofit: Retrofit): WeatherService =
        retrofit.create(WeatherService::class.java)
}