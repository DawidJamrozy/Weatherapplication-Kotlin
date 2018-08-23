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
                                { locationListener.onLocationError(it) }
                        )
        )
    }

    override fun clear() {
        compositeDisposable.clear()
    }

}

interface LocationListener {

    fun onLocationUpdate(currentLocation: CurrentLocation)
    fun onLocationError(error: Throwable)

}