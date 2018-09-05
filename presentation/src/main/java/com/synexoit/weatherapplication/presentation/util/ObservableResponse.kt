package com.synexoit.weatherapplication.presentation.util

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
    protected abstract fun saveResponseAndReturnResult(item: Result): Resource<Result>

    @MainThread
    protected abstract fun shouldFetchNewData(data: Result?): Boolean

    @MainThread
    protected abstract fun loadFromDatabase(): Maybe<Result>

    @MainThread
    protected abstract fun createCall(): Maybe<Resource<Result>>

    fun fetchData(): Maybe<Resource<Result>> {
        return loadFromDatabase()
                .filter { !shouldFetchNewData(it) }
                .map { Resource.success(it) }
                .switchIfEmpty(createCall().
                        map {
                            saveResponseAndReturnResult(it.data!!)
                        }
                )
                .onErrorReturn { Resource.error(it.message ?: ERROR_MESSAGE) }
    }
}