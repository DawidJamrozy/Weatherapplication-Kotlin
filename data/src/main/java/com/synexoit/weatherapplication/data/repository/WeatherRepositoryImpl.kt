package com.synexoit.weatherapplication.data.repository

import com.synexoit.weatherapplication.cache.manager.SharedPreferencesManager
import com.synexoit.weatherapplication.data.entity.CityPlace
import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.mapper.CityMapper
import com.synexoit.weatherapplication.data.util.ObservableResponse
import com.synexoit.weatherapplication.data.util.RateLimiter
import com.synexoit.weatherapplication.data.util.Resource
import com.synexoit.weatherapplication.remote.api.WeatherApi
import io.reactivex.Maybe
import timber.log.Timber
import java.util.concurrent.TimeUnit
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

    //TODO 23.08.2018 by Dawid Jamroży set info when not reloaded from web
    private val repoListRateLimit = RateLimiter<String>(60, TimeUnit.SECONDS)

    override fun getCity(cityPlace: CityPlace): Maybe<Resource<City>> {
        return object : ObservableResponse<City>() {
            override fun saveResponseAndReturnResult(item: City): Resource<City> {
                Timber.d("saveCallAndReturnResult():")
                cityRepository.insertCity(item)
                repoListRateLimit.addTimeStamp(item.placeId)
                return Resource.success(item)
            }

            override fun shouldFetchNewData(data: City?): Boolean {
                Timber.d("shouldFetch():")
                return data == null || repoListRateLimit.shouldFetch(cityPlace.id)
            }

            override fun loadFromDatabase(): Maybe<City> {
                Timber.d("loadFromDb():")
                return cityRepository.getCity(cityPlace.id)
            }

            override fun createCall(): Maybe<Resource<City>> {
                Timber.d("createCall():")
                val language = sharedPreferencesManager.getString(LANGUAGE)
                val units = sharedPreferencesManager.getString(UNITS)
                return weatherApi.getCity(cityPlace.latitude.toString(), cityPlace.longitude.toString(), language, EXCLUDE, units)
                        .map { cityMapper.fromRemote(it) }
                        .map { Resource.success(it.copy(name = cityPlace.name, placeId = cityPlace.id, address = cityPlace.address)) }
            }
        }.fetchData()
    }
}

interface WeatherRepository {

    fun getCity(cityPlace: CityPlace): Maybe<Resource<City>>

}