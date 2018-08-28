package com.synexoit.weatherapplication.data.repository

import com.synexoit.weatherapplication.cache.db.AppDatabase
import com.synexoit.weatherapplication.cache.entity.*
import com.synexoit.weatherapplication.data.entity.darksky.*
import com.synexoit.weatherapplication.data.mapper.*
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Dawid on 06.05.2018.
 */
class CityRepositoryImpl @Inject constructor(private val mDatabase: AppDatabase,
                                             private val cityMapper: CityMapper,
                                             private val currentlyMapper: CurrentlyMapper,
                                             private val hourlyMapper: HourlyMapper,
                                             private val hourlyDataMapper: HourlyDataMapper,
                                             private val dailyMapper: DailyMapper,
                                             private val dailyDataMapper: DailyDataMapper) : CityRepository {

    override fun getCityPlaceIdList(): Maybe<List<String>> = mDatabase.getCityDao().getCityPlaceIdList()

    override fun getCity(placeId: String): Maybe<City> {
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

    }

    override fun changeItemsPosition(pair: List<Pair<String, Int>>): Single<Unit> {
        return Single.fromCallable { pair.forEach { mDatabase.getCityDao().swapPositions(it.second, it.first) } }
    }

    override fun insertCity(city: City) {
        val id = mDatabase.getCityDao().insert(cityMapper.toCache(city))
        insertCurrently(city.currently.copy(cityId = id))
        val hourlyId = insertHourly(city.hourly.copy(cityId = id))
        insertHourlyData(hourlyId, city.hourly.data)
        val dailyId = insertDaily(city.daily.copy(cityId = id))
        insertDailyData(dailyId, city.daily.data)
    }

    private fun insertCurrently(currently: Currently): Long =
            mDatabase.getCurrentlyDao().insert(currentlyMapper.toCache(currently))

    private fun insertHourly(hourly: Hourly): Long =
            mDatabase.getHourlyDao().insert(hourlyMapper.toCache(hourly))

    private fun insertDaily(daily: Daily): Long =
            mDatabase.getDailyDao().insert(dailyMapper.toCache(daily))

    private fun insertHourlyData(hourlyId: Long, list: List<HourlyData>) {
        list.forEach { it.hourlyId = hourlyId }
        mDatabase.getHourlyDataDao().insert(list.map { hourlyDataMapper.toCache(it) })
    }

    private fun insertDailyData(dailyId: Long, list: List<DailyData>) {
        list.forEach { it.dailyId = dailyId }
        mDatabase.getDailyDataDao().insert(list.map { dailyDataMapper.toCache(it) })
    }

    private fun getCurrently(id: Long): Maybe<CurrentlyCache> =
            mDatabase.getCurrentlyDao().getCurrently(id)

    private fun getHourly(id: Long): Maybe<HourlyCache> =
            mDatabase.getHourlyDao().getHourly(id)

    private fun getHourlyData(id: Long): Maybe<List<HourlyDataCache>> =
            mDatabase.getHourlyDataDao().getHourlyData(id)

    private fun getDaily(id: Long): Maybe<DailyCache> =
            mDatabase.getDailyDao().getDaily(id)

    private fun getDailyData(id: Long): Maybe<List<DailyDataCache>> =
            mDatabase.getDailyDataDao().getDailyData(id)

}

/**
 * Created by Dawid on 06.05.2018.
 */
interface CityRepository {

    fun getCityPlaceIdList(): Maybe<List<String>>

    fun getCity(placeId: String): Maybe<City>

    fun insertCity(city: City)

    fun changeItemsPosition(pair: List<Pair<String, Int>>): Single<Unit>
}