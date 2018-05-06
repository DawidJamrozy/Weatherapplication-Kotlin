package com.synexoit.weatherapp.di.module

import com.synexoit.weatherapp.data.repository.*
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindCityPlaceRepository(repository: CityPlaceRepositoryImpl): CityPlaceRepository

    @Binds
    internal abstract fun bindWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    internal abstract fun bindCityRepository(repository: CityRepositoryImpl): CityRepository
}