package com.synexoit.weatherapp.data.repository

import com.synexoit.weatherapp.data.entity.CityPlace
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.util.Resource
import io.reactivex.Maybe

interface WeatherRepository {

    fun getCity(cityPlace: CityPlace): Maybe<Resource<City>>

}