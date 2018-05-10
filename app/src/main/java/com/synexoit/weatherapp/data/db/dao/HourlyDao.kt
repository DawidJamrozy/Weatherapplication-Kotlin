package com.synexoit.weatherapp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.synexoit.weatherapp.data.entity.darksky.Hourly
import com.synexoit.weatherapp.data.entity.darksky.HourlyData
import io.reactivex.Maybe

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
interface HourlyDao {

    @Query("SELECT * FROM hourly WHERE id = :id LIMIT 1")
    fun getCityHourlyData(id: Long): Maybe<Hourly>

    @Insert
    fun insertHourly(hourly: Hourly): Long

    @Query("SELECT * FROM hourly_data WHERE hourlyId = :id")
    fun getHourlyData(id: Long): Maybe<List<HourlyData>>

    @Insert
    fun insertHourlyData(list: List<HourlyData>)
}