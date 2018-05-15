package com.synexoit.weatherapp.di.repository

import com.synexoit.weatherapp.data.entity.CityPreview
import com.synexoit.weatherapp.data.repository.CityPreviewRepository
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class CityPreviewRepositoryTestImpl @Inject constructor() : CityPreviewRepository {

    override fun getCityPreviewList(): Maybe<List<CityPreview>> {
        TODO("not implemented")
    }

    override fun deleteCity(placeId: String): Single<Unit> {
        TODO("not implemented")
    }

    override fun isAnyCityInDatabase(): Single<Boolean> {
        TODO("not implemented")
    }
}