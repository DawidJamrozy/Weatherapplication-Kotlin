package com.synexoit.weatherapplication.data.repository

import com.synexoit.weatherapplication.data.db.AppDatabase
import com.synexoit.weatherapplication.data.entity.CityPreview
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by Dawid on 09.05.2018.
 */
class CityPreviewRepositoryImpl @Inject constructor(private val appDatabase: AppDatabase) : CityPreviewRepository {

    override fun getCityPreviewList(): Maybe<List<CityPreview>> =
            appDatabase.getCityDao().getCityPreviewList()

    override fun deleteCity(placeId: String): Single<Unit> =
            Single.fromCallable { appDatabase.getCityDao().deleteCity(placeId) }

    override fun isAnyCityInDatabase(): Single<Boolean> {
        return appDatabase.getCityDao().isAnyCityInDatabase()
    }
}

interface CityPreviewRepository {

    fun getCityPreviewList(): Maybe<List<CityPreview>>

    fun deleteCity(placeId: String): Single<Unit>

    fun isAnyCityInDatabase(): Single<Boolean>

}

