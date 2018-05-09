package com.synexoit.weatherapp.data.repository

import com.synexoit.weatherapp.data.db.AppDatabase
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.entity.darksky.Currently
import com.synexoit.weatherapp.data.entity.darksky.Daily
import com.synexoit.weatherapp.data.entity.darksky.Hourly
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Dawid on 06.05.2018.
 */
class CityRepositoryImpl @Inject constructor(private val mDatabase: AppDatabase) : CityRepository {

    override fun getCityList(): Maybe<List<City>> = mDatabase.getCityDao().getCityList()

    override fun getCityIdList(): Maybe<List<String>> = mDatabase.getCityDao().getCityIdList()

    override fun getCity(id: Long): Single<City> {
        return mDatabase.getCityDao()
                .getCityData(id)
                .flatMap { city ->
                    getHourly(id)
                            .flatMap {
                                city.hourly = it
                                getCurrently(id)
                            }
                            .flatMap {
                                city.currently = it
                                getDaily(id)
                            }
                            .map {
                                city.daily = it
                                city
                            }
                }
    }

    override fun insertCity(city: City) {
        val id = mDatabase.getCityDao().insertCity(city)
        insertHourly(id, city.hourly!!)
        insertCurrently(id, city.currently!!)
        insertDaily(id, city.daily!!)
    }

    private fun insertHourly(cityId: Long, hourly: Hourly) {
        hourly.cityId = cityId
        mDatabase.getHourlyDao().insertHourly(hourly)
    }

    private fun insertDaily(cityId: Long, daily: Daily) {
        daily.cityId = cityId
        mDatabase.getDailyDao().insertDaily(daily)
    }

    private fun insertCurrently(cityId: Long, currently: Currently) {
        currently.cityId = cityId
        mDatabase.getCurrentlyDao().insertCurrenty(currently)
    }

    private fun getHourly(id: Long): Single<Hourly> = mDatabase.getHourlyDao().getCityHourlyData(id)

    private fun getCurrently(id: Long): Single<Currently> = mDatabase.getCurrentlyDao().getCityCurrentlyData(id)

    private fun getDaily(id: Long): Single<Daily> = mDatabase.getDailyDao().getCityDailyData(id)

}