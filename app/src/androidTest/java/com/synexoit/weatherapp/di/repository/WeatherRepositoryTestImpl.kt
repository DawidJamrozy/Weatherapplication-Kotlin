package com.synexoit.weatherapp.di.repository

import com.synexoit.weatherapp.data.entity.CityPlace
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.repository.WeatherRepository
import com.synexoit.weatherapp.util.Resource
import io.reactivex.Maybe
import javax.inject.Inject

class WeatherRepositoryTestImpl @Inject constructor() : WeatherRepository {
    override fun getCity(cityPlace: CityPlace): Maybe<Resource<City>> {
        TODO("not implemented")
    }
}