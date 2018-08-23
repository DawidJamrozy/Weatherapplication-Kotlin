package com.synexoit.weatherapplication.data.repository

/**
 * Mechanism for managing user location
 */
interface LocationRepository {
    /**
     * Checks whether location is enabled
     */
    fun isLocationEnabled(): Boolean

    /**
     * Starts retrieving user location
     */
    fun getUserLocation()

    /**
     * Stops retrieving user location, should be used in onDestroy
     */
    fun clear()
}