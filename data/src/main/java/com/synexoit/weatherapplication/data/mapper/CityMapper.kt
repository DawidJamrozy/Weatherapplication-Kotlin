package com.synexoit.weatherapplication.data.mapper

import com.synexoit.weatherapplication.cache.entity.CityCache
import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.remote.entity.CityRemote
import javax.inject.Inject

class CityMapper @Inject constructor(private val hourlyMapper: HourlyMapper,
                                     private val dailyMapper: DailyMapper,
                                     private val currentlyMapper: CurrentlyMapper) : Mapper<CityCache,CityRemote, City> {

    override fun fromCache(type: CityCache): City {
        return type.run {
            City(id, placeId, name, address, addressDescription, refreshDate, sortPosition, latitude,
                    longitude, timezone, currentlyMapper.fromCache(currentlyCache!!),
                    hourlyMapper.fromCache(hourlyCache!!), dailyMapper.fromCache(dailyCache!!))
        }
    }

    override fun toCache(type: City): CityCache {
        return type.run {
            CityCache(id, placeId, name, address, addressDescription, refreshDate, sortPosition,
                    latitude, longitude, timezone, currentlyMapper.toCache(currently!!),
                    hourlyMapper.toCache(hourly!!), dailyMapper.toCache(daily!!))
        }
    }

    override fun fromRemote(type: CityRemote): City {
        return type.run {
            City(0, placeId, name, address, addressDescription, refreshDate, sortPosition, latitude,
                    longitude, timezone, currentlyMapper.fromRemote(currentlyCache!!),
                    hourlyMapper.fromRemote(hourlyCache!!), dailyMapper.fromRemote(dailyCache!!))
        }
    }
}