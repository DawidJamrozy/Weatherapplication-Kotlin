package com.synexoit.weatherapplication.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.synexoit.weatherapplication.cache.entity.DailyCache
import com.synexoit.weatherapplication.cache.entity.DailyDataCache
import io.reactivex.Maybe

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
abstract class DailyDao : BaseDao<DailyCache> {

    @Query("SELECT * FROM dailyCache WHERE id = :id LIMIT 1")
    abstract fun getDaily(id: Long): Maybe<DailyCache>

}

@Dao
abstract class DailyDataDao: BaseDao<DailyDataCache> {

    @Query("SELECT * FROM daily_data WHERE dailyId = :id")
    abstract fun getDailyData(id: Long): Maybe<List<DailyDataCache>>

}