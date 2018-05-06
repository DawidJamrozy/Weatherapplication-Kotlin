package com.synexoit.weatherapp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.synexoit.weatherapp.data.model.darksky.Hourly
import io.reactivex.Single

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
interface HourlyDao {

    @Query("SELECT * FROM hourly WHERE id = :id LIMIT 1")
    fun getCityHourlyData(id: Long): Single<Hourly>

    @Insert
    fun insertHourly(hourly: Hourly)
}