package com.synexoit.weatherapp.data.repository

import com.synexoit.weatherapp.data.entity.darksky.City
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by Dawid on 06.05.2018.
 */
interface CityRepository {

    fun getCityList(): Maybe<List<City>>

    fun getCityPlaceIdList(): Maybe<List<String>>

    fun getCity(id: Long): Single<City>

    fun removeCity(city: City): Single<Unit>

    fun insertCity(city: City)
}