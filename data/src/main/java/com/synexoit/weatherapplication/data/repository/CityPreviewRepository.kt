package com.synexoit.weatherapplication.data.repository

import com.synexoit.weatherapplication.cache.db.AppDatabase
import com.synexoit.weatherapplication.data.entity.CityPreviewData
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Dawid on 09.05.2018.
 */
class CityPreviewRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) : CityPreviewRepository {

    override fun getCityPreviewList(): Maybe<List<CityPreviewData>> =
            appDatabase.getCityDao().getCityPreviewList().map {
                it.map { CityPreviewData(it.name, it.address, it.placeId, it.sortPosition) }
            }

    override fun deleteCity(placeId: String) = appDatabase.getCityDao().deleteCity(placeId)

    override fun isAnyCityInDatabase(): Single<Boolean> =
            appDatabase.getCityDao().isAnyCityInDatabase()

}

interface CityPreviewRepository {

    fun getCityPreviewList(): Maybe<List<CityPreviewData>>

    fun deleteCity(placeId: String)

    fun isAnyCityInDatabase(): Single<Boolean>

}

