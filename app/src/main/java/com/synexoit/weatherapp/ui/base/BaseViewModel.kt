package com.synexoit.weatherapp.ui.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by dawidjamrozy on 29.08.2017.
 */
abstract class BaseViewModel : ViewModel() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mErrorLiveData = MutableLiveData<Throwable>()

    protected open fun proceedWithError(throwable: Throwable) {
        mErrorLiveData.value = throwable
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }

	fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
	}

    fun getErrorObserver() = mErrorLiveData
}