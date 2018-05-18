package com.synexoit.weatherapp.di.module

import com.nhaarman.mockito_kotlin.mock
import com.synexoit.weatherapp.data.repository.CityPreviewRepository
import com.synexoit.weatherapp.data.repository.CityRepository
import com.synexoit.weatherapp.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestRepositoryModule {

    @Provides
    @Singleton
    fun provideWeatherRepository(): WeatherRepository = mock()

    @Provides
    @Singleton
    fun provideCityRepository(): CityRepository = mock()

    @Provides
    @Singleton
    fun provideCityPreviewRepository(): CityPreviewRepository = mock()


}