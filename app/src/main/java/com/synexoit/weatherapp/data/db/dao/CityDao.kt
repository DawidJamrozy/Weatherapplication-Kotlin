package com.synexoit.weatherapp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.synexoit.weatherapp.data.entity.CityPreview
import com.synexoit.weatherapp.data.entity.darksky.City
import io.reactivex.Maybe

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
abstract class CityDao : BaseDao<City> {

    @Query("SELECT * FROM city WHERE placeId = :placeId LIMIT 1")
    abstract fun getCity(placeId: String): Maybe<City>

    @Query("SELECT name, address, placeId FROM city")
    abstract fun getCityPreviewList(): Maybe<List<CityPreview>>

    @Query("SELECT placeId FROM city")
    abstract fun getCityPlaceIdList(): Maybe<List<String>>

    @Query("SELECT placeId FROM city WHERE placeId = :placeId LIMIT 1")
    abstract fun getCityPlaceId(placeId: String): String?

    @Query("DELETE FROM city WHERE placeId = :placeId")
    abstract fun deleteCity(placeId: String)

}