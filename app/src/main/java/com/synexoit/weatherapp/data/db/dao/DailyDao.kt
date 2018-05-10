package com.synexoit.weatherapp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.synexoit.weatherapp.data.entity.darksky.Daily
import com.synexoit.weatherapp.data.entity.darksky.DailyData
import io.reactivex.Maybe

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
abstract class DailyDao : BaseDao<Daily> {

    @Query("SELECT * FROM daily WHERE id = :id LIMIT 1")
    abstract fun getDaily(id: Long): Maybe<Daily>

}

@Dao
abstract class DailyDataDao: BaseDao<DailyData> {

    @Query("SELECT * FROM daily_data WHERE dailyId = :id")
    abstract fun getDailyData(id: Long): Maybe<List<DailyData>>

}