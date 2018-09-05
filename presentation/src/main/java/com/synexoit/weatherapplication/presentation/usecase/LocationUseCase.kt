package com.synexoit.weatherapplication.presentation.usecase

import com.synexoit.weatherapplication.data.entity.CurrentLocation
import com.synexoit.weatherapplication.data.repository.LocationRepository
import io.reactivex.Single
import javax.inject.Inject

class LocationUseCase @Inject constructor(private val locationRepository: LocationRepository) {

    fun isLocationEnabled(): Single<Boolean> = locationRepository.isLocationEnabled()

    fun getUserLocation(): Single<CurrentLocation> = locationRepository.getUserLocation()

}