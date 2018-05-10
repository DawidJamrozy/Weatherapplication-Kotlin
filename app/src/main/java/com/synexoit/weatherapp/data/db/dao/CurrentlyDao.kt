package com.synexoit.weatherapp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.synexoit.weatherapp.data.entity.darksky.Currently
import io.reactivex.Maybe

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
interface CurrentlyDao {

    @Query("SELECT * FROM currently WHERE id = :id LIMIT 1")
    fun getCityCurrentlyData(id: Long): Maybe<Currently>

    @Insert
    fun insertCurrently(currently: Currently): Long
}