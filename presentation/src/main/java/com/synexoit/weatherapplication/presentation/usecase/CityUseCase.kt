package com.synexoit.weatherapplication.presentation.usecase

import com.synexoit.weatherapplication.data.entity.darksky.City
import com.synexoit.weatherapplication.data.repository.CityRepository
import com.synexoit.weatherapplication.presentation.data.entity.MainState
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

open class CityUseCase @Inject constructor(private val cityRepository: CityRepository) {

    fun getCity(placeId: String): Maybe<City> = cityRepository.getCity(placeId)

    fun getCityPlaceIdList(): Maybe<MainState> = cityRepository.getCityPlaceIdList()
            .map { MainState(it) }

    fun swapPositionsAndUpdateDatabase(pair: List<Pair<String, Int>>): Single<Unit> {
        return Single.fromCallable { pair.forEach { cityRepository.changeItemsPosition(it) } }
    }

    open fun dupa(): Maybe<MainState> {
        return Maybe.just(MainState(listOf()))
    }
}