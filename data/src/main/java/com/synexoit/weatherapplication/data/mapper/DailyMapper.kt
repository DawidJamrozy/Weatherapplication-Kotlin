package com.synexoit.weatherapplication.data.mapper

import com.synexoit.weatherapplication.cache.entity.DailyCache
import com.synexoit.weatherapplication.data.entity.darksky.Daily
import com.synexoit.weatherapplication.remote.entity.darksky.DailyRemote
import javax.inject.Inject

class DailyMapper @Inject constructor(private val dailyDataMapper: DailyDataMapper) : Mapper<DailyCache, DailyRemote, Daily> {

    override fun fromCache(type: DailyCache): Daily {
        return type.run { Daily(id,summary,icon, data!!.map { dailyDataMapper.fromCache(it) }, cityId) }
    }

    override fun toCache(type: Daily): DailyCache {
        return type.run { DailyCache(id,summary,icon, data!!.map { dailyDataMapper.toCache(it) }, cityId) }
    }

    override fun fromRemote(type: DailyRemote): Daily {
        return type.run { Daily(0 ,summary,icon, data!!.map { dailyDataMapper.fromRemote(it) }, 0) }
    }
}