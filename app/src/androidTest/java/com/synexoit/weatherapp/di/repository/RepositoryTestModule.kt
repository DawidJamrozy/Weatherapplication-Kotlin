package com.synexoit.weatherapp.di.repository

import com.synexoit.weatherapp.data.repository.CityPreviewRepository
import com.synexoit.weatherapp.data.repository.CityRepository
import com.synexoit.weatherapp.data.repository.WeatherRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryTestModule {


    @Binds
    internal abstract fun bindWeatherRepository(repository: WeatherRepositoryTestImpl): WeatherRepository

    @Binds
    internal abstract fun bindCityRepository(repository: CityRepositoryTestImpl): CityRepository

    @Binds
    internal abstract fun bindCityPreviewRepository(repository: CityPreviewRepositoryTestImpl): CityPreviewRepository
}