package com.synexoit.weatherapplication.ui.main

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapplication.data.exceptions.Failure
import com.synexoit.weatherapplication.data.repository.CityRepository
import com.synexoit.weatherapplication.ui.base.BaseViewModel
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

    private fun handleResponse(idList: List<String>) {
        cityIdList.value = idList
    }

    fun onAddButtonClick() {
        onClickEvent.value = ADD_NEW_CITY
    }
}