package com.synexoit.weatherapplication.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.synexoit.weatherapplication.data.entity.darksky.Hourly
import com.synexoit.weatherapplication.data.entity.darksky.HourlyData
import com.synexoit.weathweatherapplicationerapp.data.db.dao.BaseDao
import io.reactivex.Maybe

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
abstract class HourlyDao : BaseDao<Hourly> {

    @Query("SELECT * FROM hourly WHERE id = :id LIMIT 1")
    abstract fun getHourly(id: Long): Maybe<Hourly>

}

@Dao
abstract class HourlyDataDao: BaseDao<HourlyData> {

    @Query("SELECT * FROM hourly_data WHERE hourlyId = :id")
    abstract fun getHourlyData(id: Long): Maybe<List<HourlyData>>

}