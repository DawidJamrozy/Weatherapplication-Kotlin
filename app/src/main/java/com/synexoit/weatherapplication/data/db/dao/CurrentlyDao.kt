package com.synexoit.weatherapplication.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.synexoit.weatherapplication.data.entity.darksky.Currently
import com.synexoit.weathweatherapplicationerapp.data.db.dao.BaseDao
import io.reactivex.Maybe

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
abstract class CurrentlyDao : BaseDao<Currently> {

    @Query("SELECT * FROM currently WHERE id = :id LIMIT 1")
    abstract fun getCurrently(id: Long): Maybe<Currently>

}