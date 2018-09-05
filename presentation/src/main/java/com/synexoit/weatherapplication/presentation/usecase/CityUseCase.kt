package com.synexoit.weatherapplication.presentation.usecase

import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.repository.CityRepository
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

open class CityUseCase @Inject constructor(private val cityRepository: CityRepository) {

    fun getCity(placeId: String): Maybe<City> = cityRepository.getCity(placeId)

    fun getCityPlaceIdList(): Maybe<List<String>> = cityRepository.getCityPlaceIdList()

    fun swapPositionsAndUpdateDatabase(pair: List<Pair<String, Int>>): Single<Unit> {
        return Single.fromCallable { pair.forEach { cityRepository.changeItemsPosition(it) } }
    }
}