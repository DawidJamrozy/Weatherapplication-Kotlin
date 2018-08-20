package com.synexoit.weatherapplication.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.synexoit.weatherapplication.cache.entity.HourlyCache
import com.synexoit.weatherapplication.cache.entity.HourlyDataCache
import io.reactivex.Maybe

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
abstract class HourlyDao : BaseDao<HourlyCache> {

    @Query("SELECT * FROM hourlyCache WHERE id = :id LIMIT 1")
    abstract fun getHourly(id: Long): Maybe<HourlyCache>

}

@Dao
abstract class HourlyDataDao: BaseDao<HourlyDataCache> {

    @Query("SELECT * FROM hourly_data WHERE hourlyId = :id")
    abstract fun getHourlyData(id: Long): Maybe<List<HourlyDataCache>>

}