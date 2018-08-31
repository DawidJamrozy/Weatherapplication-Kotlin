package com.synexoit.weatherapplication.data.repository

import com.synexoit.weatherapplication.data.entity.google.GeocodeData
import com.synexoit.weatherapplication.remote.api.GoogleApi
import io.reactivex.Single
import javax.inject.Inject

class GeocodeRepositoryImpl @Inject constructor(private val googleApi: GoogleApi,
                                                private val googleApiKey: String) : GeocodeRepository {

    companion object {
        private const val LOCATION_TYPE = "APPROXIMATE"
        private const val RESULT_TYPE = "locality"
        private const val DELIMITER = ","
    }

    override fun getGeocodeCityData(lat: Double, lng: Double, language: String): Single<GeocodeData> {
        return googleApi.getCityDetails("$lat,$lng", LOCATION_TYPE, RESULT_TYPE,
                language, googleApiKey)
                .map {
                    val city = it.results[0]
                    val name = city.formattedAddress.substringBefore(DELIMITER)
                    GeocodeData(city.placeId, city.formattedAddress, name)
                }
    }
}

interface GeocodeRepository {
    fun getGeocodeCityData(lat: Double, lng: Double, language: String): Single<GeocodeData>
}
