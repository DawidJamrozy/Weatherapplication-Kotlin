package com.synexoit.weatherapp.data.repository

import com.synexoit.weatherapp.data.db.AppDatabase
import com.synexoit.weatherapp.data.model.darksky.City
import com.synexoit.weatherapp.data.model.darksky.Currently
import com.synexoit.weatherapp.data.model.darksky.Daily
import com.synexoit.weatherapp.data.model.darksky.Hourly
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Dawid on 06.05.2018.
 */
class CityRepositoryImpl @Inject constructor(private val mDatabase: AppDatabase) : CityRepository {

    override fun getCityList(): Single<List<City>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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

    override fun insertCity(city: City): Single<Long> {
        return Single.just(city)
                .map {
                    val id = mDatabase.getCityDao().insertCity(city)
                    insertHourly(id, city.hourly!!)
                    insertCurrently(id, city.currently!!)
                    insertDaily(id, city.daily!!)
                    id
                }
    }

    private fun insertHourly(cityId: Long, hourly: Hourly) {
        hourly.cityId = cityId
        mDatabase.getHourlyDao().insertHourly(hourly)
    }

    private fun insertDaily(cityId: Long, daily: Daily) {
        daily.cityId = cityId
        mDatabase.getDailyDao().insertDaily(daily)
    }

    private fun insertCurrently(cityId: Long,currently: Currently){
        currently.cityId = cityId
        mDatabase.getCurrentlyDao().insertCurrenty(currently)
    }

    private fun getHourly(id: Long): Single<Hourly> {
        return mDatabase.getHourlyDao().getCityHourlyData(id)
    }

    private fun getCurrently(id: Long): Single<Currently> {
        return mDatabase.getCurrentlyDao().getCityCurrentlyData(id)
    }

    private fun getDaily(id: Long): Single<Daily> {
        return mDatabase.getDailyDao().getCityDailyData(id)
    }
}