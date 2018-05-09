package com.synexoit.weatherapp.ui.main

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapp.WeatherApplication
import com.synexoit.weatherapp.data.repository.CityRepository
import com.synexoit.weatherapp.ui.base.BaseAndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Dawid on 25.04.2018.
 */
class MainViewModel @Inject constructor(mCityRepository: CityRepository,
                                        application: WeatherApplication) : BaseAndroidViewModel(application) {

    private val cityIdList = MutableLiveData<List<String>>()

    init {
        mCityRepository.getCityPlaceIdList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { cityIdList.value = it },
                        { proceedWithError(it) }
                )
    }

    fun getCityIdList() = cityIdList

}