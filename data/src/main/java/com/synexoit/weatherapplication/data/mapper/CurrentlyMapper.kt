package com.synexoit.weatherapplication.data.mapper

import com.synexoit.weatherapplication.cache.entity.CurrentlyCache
import com.synexoit.weatherapplication.data.entity.darksky.Currently
import com.synexoit.weatherapplication.remote.entity.darksky.CurrentlyRemote
import javax.inject.Inject

class CurrentlyMapper @Inject constructor(): Mapper<CurrentlyCache, CurrentlyRemote, Currently> {

    override fun fromCache(type: CurrentlyCache): Currently {
        return type.run { Currently(id, time,summary,icon, precipIntensity, precipProbability, temperature,
                apparentTemperature, dewPoint, humidity, windSpeed, windBearing, cloudCover,
                pressure, ozone, cityId) }

    }

    override fun toCache(type: Currently): CurrentlyCache {
        return type.run { CurrentlyCache(id, time,summary,icon, precipIntensity, precipProbability, temperature,
                apparentTemperature, dewPoint, humidity, windSpeed, windBearing, cloudCover, pressure,
                ozone, cityId) }
    }

    override fun fromRemote(type: CurrentlyRemote): Currently {
        return type.run { Currently(0, time,summary,icon, precipIntensity, precipProbability, temperature,
                apparentTemperature, dewPoint, humidity, windSpeed, windBearing, cloudCover,
                pressure, ozone, 0) }
    }
}