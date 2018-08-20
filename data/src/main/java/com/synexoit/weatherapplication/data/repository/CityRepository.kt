package com.synexoit.weatherapplication.data.repository

import com.synexoit.weatherapplication.data.entity.darksky.City
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by Dawid on 06.05.2018.
 */
interface CityRepository {

    fun getCityPlaceIdList(): Maybe<List<String>>

    fun getCity(placeId: String): Maybe<City>

    fun insertCity(city: City)

    fun changeItemsPosition(pair: List<Pair<String, Int>>): Single<Unit>

    fun updateCity(city:City)
}