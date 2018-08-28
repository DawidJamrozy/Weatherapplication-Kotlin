package com.synexoit.weatherapplication.presentation.viewmodel.base

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.synexoit.weatherapplication.data.exceptions.Failure
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by dawidjamrozy on 29.08.2017.
 */
abstract class BaseViewModel : ViewModel() {

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
}