package com.synexoit.weatherapp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.synexoit.weatherapp.data.entity.darksky.Daily
import com.synexoit.weatherapp.data.entity.darksky.DailyData
import io.reactivex.Maybe

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
interface DailyDao {

    @Query("SELECT * FROM daily WHERE id = :id LIMIT 1")
    fun getCityDailyData(id: Long): Maybe<Daily>

    @Insert
    fun insertDaily(daily: Daily): Long

    @Query("SELECT * FROM daily_data WHERE dailyId = :id")
    fun getDailyData(id: Long): Maybe<List<DailyData>>

    @Insert
    fun insertDailyData(list: List<DailyData>)
}