package com.synexoit.weatherapp.data.repository

import com.synexoit.weatherapp.data.entity.darksky.City
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by Dawid on 06.05.2018.
 */
interface CityRepository {

    fun getCityPlaceIdList(): Maybe<List<String>>

    fun getCity(placeId: String): Single<City>

    fun insertCity(city: City)
}