package com.synexoit.weatherapplication.presentation.viewmodel.splash

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapplication.data.exceptions.Failure
import com.synexoit.weatherapplication.data.repository.CityPreviewRepository
import com.synexoit.weatherapplication.presentation.viewmodel.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by dawidjamrozy on 14.05.2018.
 */
class SplashViewModel @Inject constructor(private val cityPreviewRepository: CityPreviewRepository)
    : BaseViewModel() {

    val isCityTableEmpty = MutableLiveData<Boolean>()

    init {
        checkCityTableState()
    }

    private fun checkCityTableState() {
        addDisposable(cityPreviewRepository.isAnyCityInDatabase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        { processResponse(it) },
                        { handleFailure(Failure.UnknownAppError())})
        )
    }

    private fun processResponse(isTableEmpty: Boolean) {
        isCityTableEmpty.value = isTableEmpty
    }
}