package com.synexoit.weatherapplication.data.mapper

import com.synexoit.weatherapplication.cache.entity.HourlyDataCache
import com.synexoit.weatherapplication.data.entity.darksky.HourlyData
import com.synexoit.weatherapplication.remote.entity.darksky.HourlyDataRemote
import javax.inject.Inject

class HourlyDataMapper @Inject constructor() : Mapper<HourlyDataCache, HourlyDataRemote, HourlyData> {

    override fun fromCache(type: HourlyDataCache): HourlyData {
        return type.run {
            HourlyData(time, summary, icon, precipIntensity, precipProbability, temperature,
                    apparentTemperature, dewPoint, humidity, windSpeed, windBearing, cloudCover, pressure,
                    ozone, precipType, hourlyId, roomId)
        }
    }

    override fun toCache(type: HourlyData): HourlyDataCache {
        return type.run {
            HourlyDataCache(time, summary, icon, precipIntensity, precipProbability, temperature,
                    apparentTemperature, dewPoint, humidity, windSpeed, windBearing, cloudCover, pressure,
                    ozone, precipType, hourlyId, roomId)
        }
    }

    override fun fromRemote(type: HourlyDataRemote): HourlyData {
        return type.run {
            HourlyData(time, summary, icon, precipIntensity, precipProbability, temperature,
                    apparentTemperature, dewPoint, humidity, windSpeed, windBearing, cloudCover, pressure,
                    ozone, precipType, 0, 0)
        }
    }
}