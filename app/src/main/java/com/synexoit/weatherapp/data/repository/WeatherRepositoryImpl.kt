package com.synexoit.weatherapp.data.repository

import com.synexoit.weatherapp.data.api.WeatherApi
import com.synexoit.weatherapp.data.entity.darksky.City
import io.reactivex.Observable
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val mWeatherApi: WeatherApi) : WeatherRepository {

    //TODO 05.05.2018 Dawid Jamroży change to config
    private val LANGUAGE = "pl"
    private val EXCLUDE = "flags,alerts,minutely"
    private val UNITS = "ca"


    override fun getCity(lat: Double, lng: Double): Observable<City> {
        return mWeatherApi.getCity(lat.toString(), lng.toString(), LANGUAGE, EXCLUDE, UNITS)
    }
}