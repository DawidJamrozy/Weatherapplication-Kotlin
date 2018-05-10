package com.synexoit.weatherapp.data.db.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update

/**
 * Created by dawidjamrozy on 10.05.2018.
 */
interface BaseDao<in T> {

    @Insert
    fun insert(item: T): Long

    @Insert
    fun insert(vararg item: T): List<Long>

    @Insert
    @JvmSuppressWildcards
    fun insert(list: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(item: T): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(vararg item: T): Int

    @Update(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun update(list: List<T>): Int

    @Delete
    fun delete(item: T): Int

    @Delete
    fun delete(vararg item: T): Int

    @Delete
    @JvmSuppressWildcards
    fun delete(list: List<T>): Int

}