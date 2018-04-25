package com.synexoit.weatherapp.ui.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by dawidjamrozy on 29.08.2017.
 */
abstract class BaseViewModel : ViewModel() {

    private val mCompositeDisposable: CompositeDisposable = CompositeDisposable()
    private val mErrorLiveData = MutableLiveData<Throwable>()

    protected open fun proceedWithError(throwable: Throwable) {
        mErrorLiveData.value = throwable
    }

    override fun onCleared() {
        mCompositeDisposable.clear()
    }

	fun addDisposable(disposable: Disposable) {
		mCompositeDisposable.add(disposable)
	}

    fun getErrorObserver() = mErrorLiveData
}