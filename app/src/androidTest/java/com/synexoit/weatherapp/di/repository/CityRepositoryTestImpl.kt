package com.synexoit.weatherapp.di.repository

import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.repository.CityRepository
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class CityRepositoryTestImpl @Inject constructor() : CityRepository {
    override fun getCityPlaceIdList(): Maybe<List<String>> {
        TODO("not implemented")
    }

    override fun getCity(placeId: String): Maybe<City> {
        TODO("not implemented")
    }

    override fun insertCity(city: City) {
        TODO("not implemented")
    }

    override fun changeItemsPosition(pair: List<Pair<String, Int>>): Single<Unit> {
        TODO("not implemented")
    }

    override fun updateCity(city: City) {
        TODO("not implemented")
    }
}