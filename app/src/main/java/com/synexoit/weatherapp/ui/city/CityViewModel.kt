package com.synexoit.weatherapp.ui.city

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.entity.darksky.DayData
import com.synexoit.weatherapp.data.repository.CityRepository
import com.synexoit.weatherapp.data.repository.WeatherRepository
import com.synexoit.weatherapp.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Dawid on 05.05.2018.
 */
class CityViewModel @Inject constructor(private val cityRepository: CityRepository,
                                        private val weatherRepository: WeatherRepository) : BaseViewModel() {

    val city = MutableLiveData<City>()
    val dayDataList = MutableLiveData<MutableList<DayData>>()
    val event = MutableLiveData<Int>()

    fun loadCityFromDatabase(placeId: String) {
        addDisposable(cityRepository.getCity(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                //TODO 14.05.2018 by Dawid Jamroży
                //.doOnError { proceedWithError(it) }
                .subscribe({ processResponse(it) }))
    }

    fun refreshWeatherData() {
        city.value?.let {
            addDisposable(weatherRepository.getCity(it.toCityPlace())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    //TODO 14.05.2018 by Dawid Jamroży
                    //.doOnError { proceedWithError(it) }
                    .subscribe({ processResponse(it.data) }))
        }
    }

    private fun processResponse(data: City?) {
        data?.let { city.value = it }

        data?.daily?.data?.let {
            val temporaryDayDataList = mutableListOf<DayData>()
            it.take(7).forEach { temporaryDayDataList.add(DayData(it.temperatureMin.toInt(),
                    it.temperatureMax.toInt(), it.icon, it.time)) }
            dayDataList.value = temporaryDayDataList
        }
    }

    //TODO 11.05.2018 by Dawid Jamroży replace with proper navigation
    fun openWebsite() {
        event.value = 0
    }
}