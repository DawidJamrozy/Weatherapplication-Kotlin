package com.synexoit.weatherapplication.presentation.viewmodel.main

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapplication.presentation.data.entity.MainState
import com.synexoit.weatherapplication.presentation.usecase.CityUseCase
import com.synexoit.weatherapplication.presentation.viewmodel.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Dawid on 25.04.2018.
 */
class MainViewModel @Inject constructor(private val cityUseCase: CityUseCase) : BaseViewModel() {

    val cityIdList = MutableLiveData<MainState>()

    companion object {
        const val ADD_NEW_CITY = 1000
    }

    fun loadCityIdListFromDatabase() {
        addDisposable(cityUseCase.dupa()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { handleFailure(it) }
                .subscribe { handleResponse(it) })
    }

    private fun handleResponse(mainState: MainState) {
        cityIdList.value = mainState
    }

    fun onAddButtonClick() {
        onClickEvent.value = ADD_NEW_CITY
    }
}