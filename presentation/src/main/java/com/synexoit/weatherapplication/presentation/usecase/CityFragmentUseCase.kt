package com.synexoit.weatherapplication.presentation.usecase

import com.synexoit.weatherapplication.cache.db.AppDatabase
import com.synexoit.weatherapplication.data.mapper.*
import com.synexoit.weatherapplication.data.repository.CityRepository
import com.synexoit.weatherapplication.data.repository.WeatherRepository
import javax.inject.Inject

class CityFragmentUseCase @Inject constructor(private val cityRepository: CityRepository,
                                              private val weatherRepository: WeatherRepository,
                                              private val mDatabase: AppDatabase,
                                              private val cityMapper: CityMapper,
                                              private val currentlyMapper: CurrentlyMapper,
                                              private val hourlyMapper: HourlyMapper,
                                              private val hourlyDataMapper: HourlyDataMapper,
                                              private val dailyMapper: DailyMapper,
                                              private val dailyDataMapper: DailyDataMapper)  {
/*
    fun getCity(placeId: String): Maybe<City> {
        return mDatabase.getCityDao()
                .getCity(placeId)
                .flatMap { city ->
                    getCurrently(city.id)
                            .map { city.currentlyCache = it }
                            .flatMap {
                                getHourly(city.id)
                                        .flatMap { hourly ->
                                            getHourlyData(hourly.id)
                                                    .map { hourlyData ->
                                                        hourly.data = hourlyData
                                                        city.hourlyCache = hourly
                                                    }
                                        }
                            }.flatMap {
                                getDaily(city.id)
                                        .flatMap { daily ->
                                            getDailyData(daily.id)
                                                    .map { dailyData ->
                                                        daily.data = dailyData
                                                        city.dailyCache = daily
                                                        city
                                                    }
                                        }
                            }
                }
                .map { cityMapper.fromCache(it) }

    }*/


}