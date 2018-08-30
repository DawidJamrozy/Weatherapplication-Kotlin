package com.synexoit.weatherapplication.presentation.usecase

import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.repository.WeatherRepository
import com.synexoit.weatherapplication.presentation.data.entity.CityPlace
import com.synexoit.weatherapplication.presentation.util.ObservableResponse
import com.synexoit.weatherapplication.presentation.util.RateLimiter
import com.synexoit.weatherapplication.presentation.util.Resource
import io.reactivex.Maybe
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    private val repoListRateLimit = RateLimiter<String>(60, TimeUnit.SECONDS)

    fun getCity(cityPlace: CityPlace): Maybe<Resource<City>> {
        return object : ObservableResponse<City>() {
            override fun saveResponseAndReturnResult(item: City): Resource<City> {
                Timber.d("saveCallAndReturnResult():")
                weatherRepository.insertCity(item)
                repoListRateLimit.addTimeStamp(item.placeId)
                return Resource.success(item)
            }

            override fun shouldFetchNewData(data: City?): Boolean {
                Timber.d("shouldFetch():")
                return data == null || repoListRateLimit.shouldFetch(cityPlace.id)
            }

            override fun loadFromDatabase(): Maybe<City> {
                Timber.d("loadFromDb():")
                return weatherRepository.getCity(cityPlace.id)
            }

            override fun createCall(): Maybe<Resource<City>> {
                Timber.d("createCall():")
                return weatherRepository.createRemoteCall(cityPlace.latitude, cityPlace.longitude)
                        .map { Resource.success(it.copy(name = cityPlace.name, placeId = cityPlace.id, address = cityPlace.address)) }
            }
        }.fetchData()
    }
}