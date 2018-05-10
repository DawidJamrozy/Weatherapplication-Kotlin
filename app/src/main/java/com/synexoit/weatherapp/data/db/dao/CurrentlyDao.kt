package com.synexoit.weatherapp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.synexoit.weatherapp.data.entity.darksky.Currently
import io.reactivex.Maybe

/**
 * Created by Dawid on 05.05.2018.
 */
@Dao
abstract class CurrentlyDao : BaseDao<Currently> {

    @Query("SELECT * FROM currently WHERE id = :id LIMIT 1")
    abstract fun getCurrently(id: Long): Maybe<Currently>

}