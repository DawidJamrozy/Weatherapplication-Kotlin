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

    override fun getCityPlaceIdList(): Maybe<List<String>> = mDatabase.getCityDao().getCityPlaceIdList()

    override fun getCity(placeId: String): Single<City> {
        return mDatabase.getCityDao()
                .getCityData(placeId)
                .flatMapSingle { city ->
                    getHourly(city.id)
                            .flatMap {
                                city.hourly = it
                                getCurrently(city.id)
                            }
                            .flatMap {
                                city.currently = it
                                getDaily(city.id)
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

    private fun insertHourly(cityId: Long, hourly: Hourly) =
            mDatabase.getHourlyDao().insertHourly(hourly.copy(cityId = cityId))

    private fun insertDaily(cityId: Long, daily: Daily) =
            mDatabase.getDailyDao().insertDaily(daily.copy(cityId = cityId))

    private fun insertCurrently(cityId: Long, currently: Currently) =
            mDatabase.getCurrentlyDao().insertCurrenty(currently.copy(cityId = cityId))

    private fun getHourly(id: Long): Single<Hourly> =
            mDatabase.getHourlyDao().getCityHourlyData(id)

    private fun getCurrently(id: Long): Single<Currently> =
            mDatabase.getCurrentlyDao().getCityCurrentlyData(id)

    private fun getDaily(id: Long): Single<Daily> =
            mDatabase.getDailyDao().getCityDailyData(id)

}