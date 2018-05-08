package com.synexoit.weatherapp.di.module

import com.synexoit.weatherapp.data.repository.CityRepository
import com.synexoit.weatherapp.data.repository.CityRepositoryImpl
import com.synexoit.weatherapp.data.repository.WeatherRepository
import com.synexoit.weatherapp.data.repository.WeatherRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindWeatherRepository(repository: WeatherRepositoryImpl): WeatherRepository

    @Binds
    internal abstract fun bindCityRepository(repository: CityRepositoryImpl): CityRepository
}