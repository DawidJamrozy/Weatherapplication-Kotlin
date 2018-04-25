package com.synexoit.weatherapp.ui.base

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapp.WeatherApplication
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Dawid on 13.01.2018.
 */
abstract class BaseAndroidViewModel(application: WeatherApplication) : AndroidViewModel(application) {

	private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
	private val mErrorLiveData = MutableLiveData<Throwable>()

	open protected fun proceedWithError(throwable: Throwable) {
		mErrorLiveData.value = throwable
	}

	protected fun proceedWithErrorOnBackgroundThead(throwable: Throwable) {
		mErrorLiveData.postValue(throwable)
	}

	override fun onCleared() {
		mCompositeDisposable.clear()
	}

	fun addDisposable(disposable: Disposable) {
		mCompositeDisposable.add(disposable)
	}

	fun getErrorObserver() = mErrorLiveData
}