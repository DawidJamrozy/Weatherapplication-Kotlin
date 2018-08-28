package com.synexoit.weatherapplication.data.usecase

import com.synexoit.weatherapplication.data.repository.CityRepository
import io.reactivex.Maybe
import javax.inject.Inject

class MainActivityUseCase @Inject constructor(private val cityRepository: CityRepository) {

    fun getCityPlaceIdList(): Maybe<List<String>> = cityRepository.getCityPlaceIdList()

}