package com.synexoit.weatherapplication.data.repository

import com.synexoit.weatherapplication.data.entity.CurrentLocation
import com.synexoit.weatherapplication.data.util.LocationManager
import io.reactivex.Single
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(private val locationManager: LocationManager) : LocationRepository {

    override fun isLocationEnabled(): Single<Boolean> = locationManager.isLocationEnabled()

    override fun getUserLocation(): Single<CurrentLocation> = locationManager.getLastKnownLocation()

}

/**
 * Mechanism for managing user location
 */
interface LocationRepository {
    /**
     * Checks whether location is enabled
     */
    fun isLocationEnabled(): Single<Boolean>

    /**
     * Starts retrieving user location
     */
    fun getUserLocation(): Single<CurrentLocation>

}