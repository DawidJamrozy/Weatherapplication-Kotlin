package com.synexoit.weatherapplication.ui.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.support.annotation.StringRes
import com.synexoit.weatherapplication.data.exceptions.Failure
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by Dawid on 13.01.2018.
 */
abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {

	private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val failure = MutableLiveData<Failure>()
    val onClickEvent = MutableLiveData<Int>()

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
            = getApplication<Application>().getString(stringResId)

    fun getString(@StringRes stringResId: Int, vararg  formatArgs: Any)
            = getApplication<Application>().getString(stringResId, formatArgs)
}