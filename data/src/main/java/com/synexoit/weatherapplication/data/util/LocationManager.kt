package com.synexoit.weatherapplication.data.util

import android.annotation.SuppressLint
import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.HandlerThread
import com.synexoit.weatherapplication.data.entity.CurrentLocation
import com.synexoit.weatherapplication.data.exceptions.Failure
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class LocationManager @Inject constructor(context: Context) {

    private var locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val locationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {}

        override fun onStatusChanged(s: String, i: Int, bundle: Bundle) {}

        override fun onProviderEnabled(s: String) {}

        override fun onProviderDisabled(s: String) {}
    }

    fun isLocationEnabled(): Single<Boolean> {
        return Single.just(isEnabled())
    }

    @SuppressLint("MissingPermission")
    fun getLastKnownLocation(): Single<CurrentLocation> {
        return Single.create { emitter ->
            var location: Location? = null

            updateLocationTracking()
            val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            if (isGpsEnabled) location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)


            if (isNetworkEnabled && location == null) location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

            if (location == null)
                emitter.onError(Failure.NoLocationAvailable())
            else
                emitter.onSuccess(CurrentLocation(location.latitude, location.longitude))
        }
    }

    @SuppressLint("MissingPermission")
    private fun updateLocationTracking() {
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        val handlerThread = HandlerThread("asd")
        handlerThread.start()

        if (isNetworkEnabled) {
            Timber.d("Using network provider.")
            locationManager.run {
                removeUpdates(locationListener)

                requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0f, locationListener,handlerThread.looper)
                getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
        }

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (isGpsEnabled) {
            Timber.d("Using GPS provider.")
            locationManager.run {
                removeUpdates(locationListener)
                requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0f, locationListener,handlerThread.looper)
                getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
        }

        val criteria = Criteria()
        criteria.run {
            accuracy = Criteria.ACCURACY_COARSE
            isAltitudeRequired = false
            isSpeedRequired = false
            isBearingRequired = false
            isCostAllowed = false
        }

        locationManager.requestSingleUpdate(criteria, locationListener, null)
    }

    private fun isEnabled(): Boolean = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}