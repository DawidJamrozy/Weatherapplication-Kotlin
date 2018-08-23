package com.synexoit.weatherapplication.data.mapper

import com.synexoit.weatherapplication.cache.entity.HourlyCache
import com.synexoit.weatherapplication.data.entity.darksky.Hourly
import com.synexoit.weatherapplication.remote.entity.darksky.HourlyRemote
import javax.inject.Inject

class HourlyMapper @Inject constructor(private val mapper: HourlyDataMapper) : Mapper<HourlyCache, HourlyRemote, Hourly> {

    override fun fromCache(type: HourlyCache): Hourly {
        return type.run { Hourly(id, summary, icon, data!!.map { mapper.fromCache(it) }, cityId) }
    }

    override fun toCache(type: Hourly): HourlyCache {
        return type.run { HourlyCache(id, summary, icon, data!!.map { mapper.toCache(it) }, cityId) }
    }

    override fun fromRemote(type: HourlyRemote): Hourly {
        return type.run { Hourly(0, summary, icon, data!!.map { mapper.fromRemote(it) }, 0) }
    }
}