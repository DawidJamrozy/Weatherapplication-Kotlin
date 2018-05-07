package com.synexoit.weatherapp.data.repository

import com.synexoit.weatherapp.data.entity.darksky.City
import io.reactivex.Single

/**
 * Created by Dawid on 06.05.2018.
 */
interface CityRepository {

    fun getCityList(): Single<List<City>>

    fun getCity(id: Long): Single<City>

    fun insertCity(city: City): Single<Long>
}