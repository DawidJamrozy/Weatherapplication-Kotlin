package com.synexoit.weatherapplication.di.module

import com.nhaarman.mockito_kotlin.mock
import com.synexoit.weatherapplication.data.repository.*
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

    @Provides
    @Singleton
    fun provide(): LocationRepository = mock()

}