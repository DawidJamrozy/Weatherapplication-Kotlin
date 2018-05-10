package com.synexoit.weatherapp.ui.city

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.repository.CityRepository
import com.synexoit.weatherapp.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Dawid on 05.05.2018.
 */
class CityViewModel @Inject constructor(private val cityRepository: CityRepository) : BaseViewModel() {

    private var city = MutableLiveData<City>()

    fun loadCityFromDatabase(placeId: String) {
        addDisposable(cityRepository.getCity(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { city.value = it },
                        { proceedWithError(it) })
        )
    }


    fun getCityObserver() = city

}