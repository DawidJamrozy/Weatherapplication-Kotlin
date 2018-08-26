package com.synexoit.weatherapplication.di.module

import com.nhaarman.mockito_kotlin.mock
import com.synexoit.weatherapplication.data.repository.CityPreviewRepository
import com.synexoit.weatherapplication.data.repository.CityRepository
import com.synexoit.weatherapplication.data.repository.GeocodeRepository
import com.synexoit.weatherapplication.data.repository.WeatherRepository
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

    @Provides
    @Singleton
    fun provideGeocodeRepository(): GeocodeRepository = mock()

}