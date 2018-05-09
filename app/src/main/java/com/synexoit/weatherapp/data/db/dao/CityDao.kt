package com.synexoit.weatherapp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.synexoit.weatherapp.data.entity.CityPreview
import com.synexoit.weatherapp.data.entity.darksky.City
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
interface CityDao {

    @Query("SELECT * FROM city WHERE id = :id LIMIT 1")
    fun getCityData(id: Long): Single<City>

    @Query("SELECT * FROM city WHERE placeId = :placeId LIMIT 1")
    fun getCityData(placeId: String): Maybe<City>

    @Query("SELECT name, address, placeId FROM city")
    fun getCityPreviewList(): Maybe<List<CityPreview>>

    @Query("SELECT * FROM city")
    fun getCityList(): Maybe<List<City>>

    @Query("SELECT id FROM city")
    fun getCityIdList(): Maybe<List<Long>>

    @Query("SELECT placeId FROM city")
    fun getCityPlaceIdList(): Maybe<List<String>>

    @Insert
    fun insertCity(city: City): Long

    @Query("DELETE FROM city WHERE placeId = :placeId")
    fun deleteCity(placeId: String)

}