package com.synexoit.weatherapplication.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.synexoit.weatherapplication.data.entity.CityPreview
import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weathweatherapplicationerapp.data.db.dao.BaseDao
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
abstract class CityDao : BaseDao<City> {

    @Query("SELECT * FROM city WHERE placeId = :placeId LIMIT 1")
    abstract fun getCity(placeId: String): Maybe<City>

    @Query("SELECT name, address, placeId, sortPosition FROM city ORDER BY sortPosition ASC")
    abstract fun getCityPreviewList(): Maybe<List<CityPreview>>

    @Query("SELECT placeId FROM city ORDER BY sortPosition ASC")
    abstract fun getCityPlaceIdList(): Maybe<List<String>>

    @Query("SELECT placeId FROM city WHERE placeId = :placeId LIMIT 1")
    abstract fun getCityPlaceId(placeId: String): String?

    @Query("DELETE FROM city WHERE placeId = :placeId")
    abstract fun deleteCity(placeId: String)

    @Query("UPDATE city SET sortPosition = :sortPosition WHERE placeId = :placeId")
    abstract fun swapPositions(sortPosition: Int, placeId: String)

    @Query("SELECT CASE WHEN EXISTS (SELECT * FROM city LIMIT 1) THEN CAST (0 AS BIT) ELSE CAST (1 AS BIT) END")
    abstract fun isAnyCityInDatabase(): Single<Boolean>

}