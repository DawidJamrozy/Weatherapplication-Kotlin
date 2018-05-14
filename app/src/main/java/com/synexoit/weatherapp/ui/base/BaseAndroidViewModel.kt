package com.synexoit.weatherapp.ui.base

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.StringRes
import com.synexoit.weatherapp.WeatherApplication
import com.synexoit.weatherapp.data.exceptions.Failure
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Dawid on 13.01.2018.
 */
abstract class BaseAndroidViewModel(application: WeatherApplication) : AndroidViewModel(application) {

	private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val failure = MutableLiveData<Failure>()

	protected open fun handleFailure(throwable: Failure) {
        failure.value = throwable
	}

	protected fun handleFailureFromBackgroundThread(throwable: Failure) {
        failure.postValue(throwable)
	}

	override fun onCleared() {
        compositeDisposable.clear()
	}

	fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
	}

    fun getString(@StringRes stringResId: Int)
            = getApplication<WeatherApplication>().getString(stringResId)

    fun getString(@StringRes stringResId: Int, vararg  formatArgs: Any)
            = getApplication<WeatherApplication>().getString(stringResId, formatArgs)
}