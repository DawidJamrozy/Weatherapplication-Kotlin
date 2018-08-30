package com.synexoit.weatherapplication.presentation.usecase

import com.synexoit.weatherapplication.data.repository.CityPreviewRepository
import com.synexoit.weatherapplication.presentation.data.entity.CityPreview
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

class CityPreviewUseCase @Inject constructor(private val cityPreviewRepository: CityPreviewRepository) {


    fun getCityPreviewList(): Maybe<List<CityPreview>> {
        return cityPreviewRepository.getCityPreviewList().map {
            it.map { CityPreview(it.name, it.address, it.placeId, it.sortPosition) }
        }
    }

    fun deleteCity(placeId: String): Single<Unit> = Single.fromCallable { cityPreviewRepository.deleteCity(placeId) }

    fun isAnyCityInDatabase(): Single<Boolean> = cityPreviewRepository.isAnyCityInDatabase()

}