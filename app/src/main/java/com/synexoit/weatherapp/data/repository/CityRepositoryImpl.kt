package com.synexoit.weatherapp.data.repository

import com.synexoit.weatherapp.data.db.AppDatabase
import com.synexoit.weatherapp.data.entity.darksky.*
import io.reactivex.Maybe
import javax.inject.Inject

/**
 * Created by Dawid on 06.05.2018.
 */
class CityRepositoryImpl @Inject constructor(private val mDatabase: AppDatabase) : CityRepository {

    override fun getCityPlaceIdList(): Maybe<List<String>> = mDatabase.getCityDao().getCityPlaceIdList()

    override fun getCity(placeId: String): Maybe<City> {
        return mDatabase.getCityDao()
                .getCityData(placeId)
                .flatMap { city ->
                    getCurrently(city.id)
                            .map { city.currently = it }
                            .flatMap {
                                getHourly(city.id)
                                        .flatMap { hourly ->
                                            getHourlyData(hourly.id)
                                                    .map {
                                                        hourly.data = it
                                                        city.hourly = hourly
                                                    }
                                        }
                            }.flatMap {
                                getDaily(city.id)
                                        .flatMap { daily ->
                                            getDailyData(daily.id)
                                                    .map {
                                                        daily.data = it
                                                        city.daily = daily
                                                        city
                                                    }
                                        }
                            }
                }
    }

    override fun insertCity(city: City) {
        val id = mDatabase.getCityDao().insertCity(city)
        insertCurrently(id, city.currently!!)
        val hourlyId = insertHourly(id, city.hourly!!)
        insertHourlyData(hourlyId, city.hourly!!.data!!)
        val dailyId = insertDaily(id, city.daily!!)
        insertDailyData(dailyId, city.daily!!.data!!)
    }

    private fun insertCurrently(cityId: Long, currently: Currently): Long =
            mDatabase.getCurrentlyDao().insertCurrently(currently.copy(cityId = cityId))

    private fun insertHourly(cityId: Long, hourly: Hourly): Long =
            mDatabase.getHourlyDao().insertHourly(hourly.copy(cityId = cityId))

    private fun insertHourlyData(hourlyId: Long, list: List<HourlyData>) {
        list.forEach { it.hourlyId = hourlyId }
        mDatabase.getHourlyDao().insertHourlyData(list)
    }

    private fun insertDaily(cityId: Long, daily: Daily): Long =
            mDatabase.getDailyDao().insertDaily(daily.copy(cityId = cityId))

    private fun insertDailyData(dailyId: Long, list: List<DailyData>) {
        list.forEach { it.dailyId = dailyId }
        mDatabase.getDailyDao().insertDailyData(list)
    }

    private fun getCurrently(id: Long): Maybe<Currently> =
            mDatabase.getCurrentlyDao().getCityCurrentlyData(id)

    private fun getHourly(id: Long): Maybe<Hourly> =
            mDatabase.getHourlyDao().getCityHourlyData(id)

    private fun getHourlyData(id: Long): Maybe<List<HourlyData>> =
            mDatabase.getHourlyDao().getHourlyData(id)

    private fun getDaily(id: Long): Maybe<Daily> =
            mDatabase.getDailyDao().getCityDailyData(id)

    private fun getDailyData(id: Long): Maybe<List<DailyData>> =
            mDatabase.getDailyDao().getDailyData(id)

}