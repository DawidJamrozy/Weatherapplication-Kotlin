package com.synexoit.weatherapplication.data.repository

import com.synexoit.weatherapplication.cache.manager.SharedPreferencesManager
import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.mapper.CityMapper
import com.synexoit.weatherapplication.remote.api.WeatherApi
import io.reactivex.Maybe
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val weatherApi: WeatherApi,
                                                private val cityRepository: CityRepository,
                                                private val sharedPreferencesManager: SharedPreferencesManager,
                                                private val cityMapper: CityMapper) : WeatherRepository {

    companion object {
        private const val LANGUAGE = "language"
        private const val EXCLUDE = "flags,alerts,minutely"
        private const val UNITS = "unit"
    }

    override fun createRemoteCall(latitude: Double, longitude: Double): Maybe<City> {
        val language = sharedPreferencesManager.getString(LANGUAGE)
        val units = sharedPreferencesManager.getString(UNITS)
        return weatherApi.getCity(latitude.toString(), longitude.toString(), language, EXCLUDE, units)
                .map { cityMapper.fromRemote(it) }
    }

    override fun insertCity(city: City) {
        cityRepository.insertCity(city)
    }

    override fun getCity(id: String): Maybe<City> {
        return cityRepository.getCity(id)
    }
}

interface WeatherRepository {

    fun createRemoteCall(latitude: Double, longitude: Double): Maybe<City>

    fun insertCity(city: City)

    fun getCity(id: String): Maybe<City>

}