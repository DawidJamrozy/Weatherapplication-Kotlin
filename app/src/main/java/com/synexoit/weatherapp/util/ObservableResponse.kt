package com.synexoit.weatherapp.util

import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import io.reactivex.Maybe

/**
 * Created by dawidjamrozy on 08.05.2018.
 */
abstract class ObservableResponse<Result> {

    private val ERROR_MESSAGE = "ERROR"

    @WorkerThread
    protected abstract fun saveCallResult(item: Result)

    @MainThread
    protected abstract fun shouldFetch(data: Result?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): Maybe<Result>

    @MainThread
    protected abstract fun createCall(): Maybe<Resource<Result>>

    fun fetchData(): Maybe<Resource<Result>> {
        return loadFromDb()
                .filter { !shouldFetch(it) }
                .map { Resource.success(it) }
                .switchIfEmpty(
                        createCall()
                                .map {
                                    saveCallResult(it.data!!)
                                    it
                                })
                .onErrorReturn { Resource.error(it.message ?: ERROR_MESSAGE) }
    }
}