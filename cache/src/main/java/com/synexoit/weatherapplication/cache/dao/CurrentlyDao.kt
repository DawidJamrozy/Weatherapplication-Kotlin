package com.synexoit.weatherapplication.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.synexoit.weatherapplication.cache.entity.CurrentlyCache
import io.reactivex.Maybe

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
abstract class CurrentlyDao : BaseDao<CurrentlyCache> {

    @Query("SELECT * FROM currentlyCache WHERE id = :id LIMIT 1")
    abstract fun getCurrently(id: Long): Maybe<CurrentlyCache>

}