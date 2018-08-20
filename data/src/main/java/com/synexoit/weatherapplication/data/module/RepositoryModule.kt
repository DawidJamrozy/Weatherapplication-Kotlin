package com.synexoit.weatherapplication.data.module

import com.synexoit.weatherapplication.data.repository.*
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    internal abstract fun bindCityRepository(repository: CityRepositoryImpl): CityRepository

    @Binds
    internal abstract fun bindCityPreviewRepository(repository: CityPreviewRepositoryImpl): CityPreviewRepository
}