package com.synexoit.weatherapp.data.repository

import com.synexoit.weatherapp.data.model.CityPlace
import io.reactivex.Single

interface CityPlaceRepository {

    fun insertCityPlace(place: CityPlace)

    fun insertCityPlaceList(list: List<CityPlace>)

    fun getCityPlace(id: String): Single<CityPlace>

    fun getAllCityPlaces(): Single<List<CityPlace>>
}