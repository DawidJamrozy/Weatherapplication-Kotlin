package com.synexoit.weatherapp.ui.main

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapp.data.exceptions.Failure
import com.synexoit.weatherapp.data.repository.CityRepository
import com.synexoit.weatherapp.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Dawid on 25.04.2018.
 */
class MainViewModel @Inject constructor(private val mCityRepository: CityRepository) : BaseViewModel() {

    val cityIdList = MutableLiveData<List<String>>()

    companion object {
        const val ADD_NEW_CITY = 1000
    }

    init {
        loadCityIdListFromDatabase()
    }

    fun loadCityIdListFromDatabase() {
        mCityRepository.getCityPlaceIdList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { handleResponse(it) },
                        { handleFailure(Failure.UnknownAppError()) }
                )
    }

    private fun handleResponse(cityIdList: List<String>) {
        this.cityIdList.value = cityIdList
    }

    fun onAddButtonClick() {
        onClickEvent.value = ADD_NEW_CITY
    }
}