package com.synexoit.weatherapplication.data.repository

import com.synexoit.weatherapplication.remote.api.GoogleApi
import com.synexoit.weatherapplication.remote.entity.google.GeocodeCity
import io.reactivex.Single
import javax.inject.Inject

class GeocodeRepositoryImpl @Inject constructor(private val googleApi: GoogleApi,
                                                private val googleApiKey: String) : GeocodeRepository {

    companion object {
        private const val LOCATION_TYPE = "APPROXIMATE"
        private const val RESULT_TYPE = "locality"
        private const val LANGUAGE = "language"
        private const val DELIMITER = ","
    }

    override fun getGeocodeCityData(lat: Double, lng: Double, language: String): Single<GeocodeCity> {
        return googleApi.getCityDetails("$lat,$lng", LOCATION_TYPE, RESULT_TYPE,
                language, googleApiKey)
    }
}

interface GeocodeRepository {
    fun getGeocodeCityData(lat: Double, lng: Double, language: String): Single<GeocodeCity>
}
