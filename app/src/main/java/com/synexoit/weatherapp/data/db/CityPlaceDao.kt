package com.synexoit.weatherapp.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.synexoit.weatherapp.data.model.CityPlace
import io.reactivex.Single

@Dao
interface CityPlaceDao {

    @Query("SELECT * FROM city_place WHERE id = :cityId LIMIT 1")
    fun getPlace(cityId: String): Single<CityPlace>

    @Query("SELECT * FROM city_place")
    fun getPlaces(): Single<List<CityPlace>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlace(place: CityPlace)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaceList(list: List<CityPlace>)
}