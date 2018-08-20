package com.synexoit.weatherapplication.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.synexoit.weatherapplication.cache.entity.CityCache
import com.synexoit.weatherapplication.cache.entity.CityPreviewCache
import io.reactivex.Maybe
import io.reactivex.Single

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
abstract class CityDao : BaseDao<CityCache> {

    @Query("SELECT * FROM city WHERE placeId = :placeId LIMIT 1")
    abstract fun getCity(placeId: String): Maybe<CityCache>

    @Query("SELECT name, address, placeId, sortPosition FROM city ORDER BY sortPosition ASC")
    abstract fun getCityPreviewList(): Maybe<List<CityPreviewCache>>

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