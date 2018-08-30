package com.synexoit.weatherapplication.presentation.usecase

import com.synexoit.weatherapplication.cache.manager.SharedPreferencesManager
import com.synexoit.weatherapplication.data.repository.GeocodeRepository
import com.synexoit.weatherapplication.presentation.data.entity.CityPlace
import io.reactivex.Single
import javax.inject.Inject

class GeocodeUseCase @Inject constructor(private val geocodeRepository: GeocodeRepository,
                                         private val sharedPreferencesManager: SharedPreferencesManager) {

    companion object {
        private const val LANGUAGE = "language"
        private const val DELIMITER = ","
    }

    fun getGeocodeCityData(lat: Double, lng: Double): Single<CityPlace> {
        val language = sharedPreferencesManager.getString(LANGUAGE)
        return geocodeRepository.getGeocodeCityData(lat, lng, language)
                .map {
                    val city = it.results[0]
                    val name = city.formattedAddress.substringBefore(DELIMITER)
                    CityPlace(name, city.formattedAddress, lat, lng, city.placeId)
                }
    }
}