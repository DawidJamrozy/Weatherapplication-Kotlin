package com.synexoit.weatherapp.data.repository

import com.synexoit.weatherapp.data.db.AppDatabase
import com.synexoit.weatherapp.data.model.CityPlace
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CityPlaceRepositoryImpl @Inject constructor(private val mDatabase: AppDatabase) : CityPlaceRepository {

    override fun insertCityPlace(place: CityPlace) {
        mDatabase.getCityPlaceDao().insertPlace(place)
    }

    override fun insertCityPlaceList(list: List<CityPlace>) {
        mDatabase.getCityPlaceDao().insertPlaceList(list)
    }

    override fun getCityPlace(id: String): Single<CityPlace> {
        return mDatabase.getCityPlaceDao().getPlace(id)
    }

    override fun getAllCityPlaces(): Single<List<CityPlace>> {
        return mDatabase.getCityPlaceDao().getPlaces()
    }
}