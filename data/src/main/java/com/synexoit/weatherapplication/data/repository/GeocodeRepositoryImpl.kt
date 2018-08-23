package com.synexoit.weatherapplication.data.repository

import com.synexoit.weatherapplication.cache.manager.SharedPreferencesManager
import com.synexoit.weatherapplication.data.entity.CityPlace
import com.synexoit.weatherapplication.remote.api.GoogleApi
import io.reactivex.Single
import javax.inject.Inject

class GeocodeRepositoryImpl @Inject constructor(private val googleApi: GoogleApi,
                                                private val googleApiKey: String,
                                                private val sharedPreferencesManager: SharedPreferencesManager) : GeocodeRepository {

    companion object {
        private const val LOCATION_TYPE = "APPROXIMATE"
        private const val RESULT_TYPE = "locality"
        private const val LANGUAGE = "language"
        private const val DELIMITER = ","
    }

    override fun getGeocodeCityData(lat: Double, lng: Double): Single<CityPlace> {
        return googleApi.getCityDetails("$lat,$lng", LOCATION_TYPE, RESULT_TYPE, sharedPreferencesManager.getString(LANGUAGE), googleApiKey)
                .map {
                    val city = it.results[0]
                    val name = city.formattedAddress.substringBefore(DELIMITER)
                    CityPlace(name, city.formattedAddress, lat, lng, city.placeId)
                }
    }
}

interface GeocodeRepository {
    fun getGeocodeCityData(lat: Double, lng: Double): Single<CityPlace>
}
