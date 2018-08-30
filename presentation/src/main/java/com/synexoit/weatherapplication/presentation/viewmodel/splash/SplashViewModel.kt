package com.synexoit.weatherapplication.presentation.viewmodel.splash

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapplication.presentation.usecase.CityPreviewUseCase
import com.synexoit.weatherapplication.presentation.viewmodel.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by dawidjamrozy on 14.05.2018.
 */
class SplashViewModel @Inject constructor(private val cityPreviewUseCase: CityPreviewUseCase)
    : BaseViewModel() {

    val isCityTableEmpty = MutableLiveData<Boolean>()

    init {
        checkCityTableState()
    }

    private fun checkCityTableState() {
        addDisposable(cityPreviewUseCase.isAnyCityInDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { handleFailure(it) }
                .subscribe { isTableEmpty -> processResponse(isTableEmpty) })
    }

    private fun processResponse(isTableEmpty: Boolean) {
        isCityTableEmpty.value = isTableEmpty
    }
}