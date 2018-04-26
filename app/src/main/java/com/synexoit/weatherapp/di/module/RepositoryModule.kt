package com.synexoit.weatherapp.di.module

import com.synexoit.weatherapp.data.repository.CityPlaceRepository
import com.synexoit.weatherapp.data.repository.CityPlaceRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    internal abstract fun bindCityPlaceRepository(repository: CityPlaceRepositoryImpl): CityPlaceRepository
}