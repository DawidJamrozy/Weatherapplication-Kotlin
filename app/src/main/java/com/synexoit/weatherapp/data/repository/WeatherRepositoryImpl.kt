package com.synexoit.weatherapp.data.repository

import com.synexoit.weatherapp.data.api.WeatherApi
import com.synexoit.weatherapp.data.entity.CityPlace
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.manager.SharedPreferencesManager
import com.synexoit.weatherapp.util.ObservableResponse
import com.synexoit.weatherapp.util.RateLimiter
import com.synexoit.weatherapp.util.Resource
import io.reactivex.Maybe
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(private val mWeatherApi: WeatherApi,
                                                private val mCityRepository: CityRepository,
                                                private val sharedPreferencesManager: SharedPreferencesManager) : WeatherRepository {

    //TODO 05.05.2018 Dawid Jamroży change to config
    private val LANGUAGE = "language"
    private val EXCLUDE = "flags,alerts,minutely"
    private val UNITS = "unit"

    private val repoListRateLimit = RateLimiter<String>(60, TimeUnit.SECONDS)

    override fun getCity(cityPlace: CityPlace): Maybe<Resource<City>> {
        return object : ObservableResponse<City>() {
            override fun saveCallAndReturnResult(item: City): Resource<City> {
                Timber.d("saveCallAndReturnResult(): ")
                mCityRepository.insertCity(item)
                repoListRateLimit.addTimeStamp(item.placeId)
                return Resource.success(item)
            }

            override fun shouldFetch(data: City?): Boolean {
                Timber.d("shouldFetch(): ")
                return data == null || repoListRateLimit.shouldFetch(cityPlace.id)
            }

            override fun loadFromDb(): Maybe<City> {
                Timber.d("loadFromDb(): ")
                return mCityRepository.getCity(cityPlace.id)
            }

            override fun createCall(): Maybe<Resource<City>> {
                Timber.d("createCall(): ")
                val language = sharedPreferencesManager.getString(LANGUAGE)
                val units = sharedPreferencesManager.getString(UNITS)
                return mWeatherApi.getCity(cityPlace.latitude.toString(), cityPlace.longitude.toString(), language, EXCLUDE, units)
                        .map { Resource.success(it.copy(name = cityPlace.name, placeId = cityPlace.id, address = cityPlace.address)) }
            }
        }.fetchData()
    }
}