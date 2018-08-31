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
    }

    fun getGeocodeCityData(lat: Double, lng: Double): Single<CityPlace> {
        val language = sharedPreferencesManager.getString(LANGUAGE)
        return geocodeRepository.getGeocodeCityData(lat, lng, language)
                .map {
                    CityPlace(it.name, it.formattedAddress, lat, lng, it.placeId)
                }
    }
}