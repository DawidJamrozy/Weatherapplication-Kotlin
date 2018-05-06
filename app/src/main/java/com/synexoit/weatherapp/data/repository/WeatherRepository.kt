package com.synexoit.weatherapp.data.repository

import com.synexoit.weatherapp.data.model.darksky.City
import io.reactivex.Observable

interface WeatherRepository {

    fun getCity(lat: Double, lng: Double): Observable<City>
}