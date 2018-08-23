package com.synexoit.weatherapplication.data.mapper

import com.synexoit.weatherapplication.cache.entity.DailyDataCache
import com.synexoit.weatherapplication.data.entity.darksky.DailyData
import com.synexoit.weatherapplication.remote.entity.darksky.DailyDataRemote
import javax.inject.Inject

class DailyDataMapper @Inject constructor() : Mapper<DailyDataCache, DailyDataRemote, DailyData> {

    override fun fromCache(type: DailyDataCache): DailyData {
        return type.run {
            DailyData(time, summary, placeId, icon, sunriseTime, sunsetTime, moonPhase, precipIntensity,
                    precipIntensityMax, precipIntensityMaxTime, precipProbability, precipType, temperatureMin,
                    temperatureMinTime, temperatureMax, temperatureMaxTime, apparentTemperatureMin,
                    apparentTemperatureMinTime, apparentTemperatureMax, apparentTemperatureMaxTime,
                    dewPoint, humidity, windSpeed, windBearing, cloudCover, pressure, ozone, dailyId, roomId)
        }
    }

    override fun toCache(type: DailyData): DailyDataCache {
        return type.run { DailyDataCache(time, summary, placeId, icon, sunriseTime, sunsetTime, moonPhase, precipIntensity,
                precipIntensityMax, precipIntensityMaxTime, precipProbability, precipType, temperatureMin,
                temperatureMinTime, temperatureMax, temperatureMaxTime, apparentTemperatureMin,
                apparentTemperatureMinTime, apparentTemperatureMax, apparentTemperatureMaxTime,
                dewPoint, humidity, windSpeed, windBearing, cloudCover, pressure, ozone, dailyId, roomId) }
    }

    override fun fromRemote(type: DailyDataRemote): DailyData {
        return type.run {
            DailyData(time, summary, placeId, icon, sunriseTime, sunsetTime, moonPhase, precipIntensity,
                    precipIntensityMax, precipIntensityMaxTime, precipProbability, precipType, temperatureMin,
                    temperatureMinTime, temperatureMax, temperatureMaxTime, apparentTemperatureMin,
                    apparentTemperatureMinTime, apparentTemperatureMax, apparentTemperatureMaxTime,
                    dewPoint, humidity, windSpeed, windBearing, cloudCover, pressure, ozone)
        }
    }
}