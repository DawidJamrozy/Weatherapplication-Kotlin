package com.synexoit.weatherapp.ui.city

import android.arch.lifecycle.MutableLiveData
import com.synexoit.weatherapp.R
import com.synexoit.weatherapp.WeatherApplication
import com.synexoit.weatherapp.data.entity.DayDetails
import com.synexoit.weatherapp.data.entity.darksky.City
import com.synexoit.weatherapp.data.entity.darksky.Currently
import com.synexoit.weatherapp.data.entity.darksky.DailyData
import com.synexoit.weatherapp.data.entity.darksky.DayData
import com.synexoit.weatherapp.data.repository.CityRepository
import com.synexoit.weatherapp.data.repository.WeatherRepository
import com.synexoit.weatherapp.ui.base.BaseAndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Dawid on 05.05.2018.
 */
class CityViewModel @Inject constructor(private val cityRepository: CityRepository,
                                        private val weatherRepository: WeatherRepository,
                                        weatherApplication: WeatherApplication) : BaseAndroidViewModel(weatherApplication) {

    companion object {
        const val OPEN_WEBSITE = 0
        const val OPEN_SETTINGS = 1
    }

    val city = MutableLiveData<City>()
    val dayDetailsList = MutableLiveData<MutableList<DayDetails>>()
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
        data?.let {
            city.value = it
            it.daily?.data?.let { createDayData(it) }
            createDayDetails(it.currently)
        }
    }

    private fun createDayData(list: List<DailyData>) {
        val temporaryDayDataList = mutableListOf<DayData>()
        list.take(7).forEach {
            temporaryDayDataList.add(DayData(it.temperatureMin.toInt(),
                    it.temperatureMax.toInt(), it.icon, it.time))
        }
        dayDataList.value = temporaryDayDataList
    }

    private fun createDayDetails(currently: Currently?) {
        currently?.let {
            val list = mutableListOf<DayDetails>()
            list.run {
                add(DayDetails(getString(R.string.wind), it.windSpeed.toInt(), getString(R.string.speed_unit), R.drawable.wind))
                add(DayDetails(getString(R.string.humidity), (it.humidity * 100).toInt(), getString(R.string.percent_unit), R.drawable.humidity))
                add(DayDetails(getString(R.string.apparent), it.apparentTemperature.toInt(), getString(R.string.degree_unit), R.drawable.temperature))
                add(DayDetails(getString(R.string.precip), it.precipIntensity.toInt(), getString(R.string.precip_unit), R.drawable.drop))
                add(DayDetails(getString(R.string.pressure), it.pressure.toInt(), getString(R.string.pressure_unit), R.drawable.pressure))
            }

            dayDetailsList.value = list
        }
    }

    fun onSettingsClick() {
        event.value = 1
    }


    //TODO 11.05.2018 by Dawid Jamroży replace with proper navigation
    fun openWebsite() {
        event.value = 0
    }
}