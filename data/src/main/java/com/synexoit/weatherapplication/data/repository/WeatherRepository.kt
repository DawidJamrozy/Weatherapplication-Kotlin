package com.synexoit.weatherapplication.data.repository

import com.synexoit.weatherapplication.data.entity.CityPlace
import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.util.Resource
import io.reactivex.Maybe

interface WeatherRepository {

    fun getCity(cityPlace: CityPlace): Maybe<Resource<City>>

}