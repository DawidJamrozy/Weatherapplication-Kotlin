package com.synexoit.weatherapp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.synexoit.weatherapp.data.model.darksky.City
import io.reactivex.Single

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
interface CityDao {

    @Query("SELECT * FROM city WHERE id = :id LIMIT 1")
    fun getCityData(id: Long): Single<City>

    @Insert
    fun insertCity(city: City): Long

}