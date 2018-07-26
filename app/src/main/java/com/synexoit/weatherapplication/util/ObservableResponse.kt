package com.synexoit.weatherapplication.util

import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import io.reactivex.Maybe

/**
 * Created by dawidjamrozy on 08.05.2018.
 */
abstract class ObservableResponse<Result> {

    companion object {
        private const val ERROR_MESSAGE = "ERROR"
    }

    @WorkerThread
    protected abstract fun saveCallAndReturnResult(item: Result): Resource<Result>

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
                .switchIfEmpty(createCall().map { saveCallAndReturnResult(it.data!!) })
                .onErrorReturn { Resource.error(it.message ?: ERROR_MESSAGE) }
    }
}