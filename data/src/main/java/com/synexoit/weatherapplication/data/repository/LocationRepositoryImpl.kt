package com.synexoit.weatherapplication.data.repository

import com.synexoit.weatherapplication.data.entity.CurrentLocation
import com.synexoit.weatherapplication.data.util.LocationManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(private val locationListener: LocationListener,
                                                 private val locationManager: LocationManager) : LocationRepository {

    private val compositeDisposable = CompositeDisposable()

    override fun isLocationEnabled(): Boolean = locationManager.isLocationEnabled()

    override fun getUserLocation() {
        compositeDisposable.add(
                locationManager.getLastKnownLocation()
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { locationListener.onLocationUpdate(it) },
                                {
                                    locationListener.onLocationError(it)
                                    it.printStackTrace()
                                }
                        )
        )
    }

    override fun clear() {
        compositeDisposable.clear()
    }

}

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

interface LocationListener {

    fun onLocationUpdate(currentLocation: CurrentLocation)
    fun onLocationError(error: Throwable)

}