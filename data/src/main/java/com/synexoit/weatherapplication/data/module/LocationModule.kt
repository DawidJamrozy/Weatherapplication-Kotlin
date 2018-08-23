package com.synexoit.weatherapplication.data.module

import android.support.v4.app.FragmentActivity
import com.synexoit.weatherapplication.data.repository.LocationListener
import com.synexoit.weatherapplication.data.repository.LocationRepository
import com.synexoit.weatherapplication.data.repository.LocationRepositoryImpl
import com.synexoit.weatherapplication.data.util.LocationManager
import dagger.Module
import dagger.Provides

@Module
class LocationModule {

    @Provides
    internal fun provideLocationManager(activity: FragmentActivity): LocationManager {
        return LocationManager(activity)
    }

    @Provides
    fun provideLocationRepository(viewModel: FragmentActivity, locationManager: LocationManager): LocationRepository {
        return LocationRepositoryImpl(viewModel as LocationListener, locationManager)
    }

}